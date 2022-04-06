package com.firethings.something.data.api

import com.firethings.something.domain.model.ParameterUnit
import com.firethings.something.data.api.model.WeatherModel
import com.firethings.something.domain.model.Coordinates
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import timber.log.Timber
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class WeatherClient {
    private val internalClient by lazy {
        OkHttpClient.Builder().build()
    }

    private val json by lazy {
        Json { ignoreUnknownKeys = true }
    }

    suspend fun getWeather(location: Coordinates, unit: ParameterUnit = ParameterUnit.METRIC): WeatherModel? {
        val (lat, lon) = location
        val result = request(
            Request.Builder()
                .url(
                    buildString {
                        append(WEATHER_BASE_URL)
                        append("/weather?lat=$lat&lon=$lon")
                        unit.parameter?.let { append("&units=$it") }
                        append("&appid=$API_KEY")
                    }).build()
        )

        val jsonString = result.content.decodeToString()
        try {
            return when (result.statusCode) {
                OK_RESPONSE_CODE -> json.decodeFromString<WeatherModel?>(jsonString)
                    ?.copy(timestamp = result.timestamp, parameterUnit = unit)
                else -> null
            }
        } catch (e: SerializationException) {
            Timber.e("WeatherEntry Serialization Exception: " + e.message)
        }

        return null
    }

    private suspend fun request(request: Request): Response = suspendCancellableCoroutine { cont ->
        val call = internalClient.newCall(request)

        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                cont.resumeWithException(e)
            }

            override fun onResponse(call: Call, response: okhttp3.Response) {
                response.receivedResponseAtMillis
                cont.resume(
                    Response(
                        statusCode = response.code,
                        content = response.body?.bytes() ?: ByteArray(0),
                        timestamp = response.receivedResponseAtMillis
                    )
                )
            }
        })

        cont.invokeOnCancellation { call.cancel() }
    }

    companion object {
        private const val OK_RESPONSE_CODE = 200
        private const val WEATHER_BASE_URL = "https://api.openweathermap.org/data/2.5"
        private const val API_KEY = "092580b329ab6b32ae4aabc5b86ba0af"
    }
}
