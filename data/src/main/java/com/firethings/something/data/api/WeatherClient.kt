package com.firethings.something.data.api

import com.firethings.something.data.BuildConfig
import com.firethings.something.data.api.model.WeatherModel
import com.firethings.something.domain.model.Coordinates
import com.firethings.something.domain.model.ParameterUnit
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherClient {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(WEATHER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val service by lazy {
        retrofit.create(WeatherService::class.java)
    }

    suspend fun getWeather(location: Coordinates, unit: ParameterUnit = ParameterUnit.Metric): WeatherModel? {
        val result = service.getWeather(location.lat, location.lon, unit.parameter, BuildConfig.OPEN_WEATHER_KEY)
        return result.body()?.copy(parameterUnit = unit, timestamp = result.raw().receivedResponseAtMillis())
    }

    companion object {
        private const val WEATHER_BASE_URL = "https://api.openweathermap.org/data/2.5/"
    }
}
