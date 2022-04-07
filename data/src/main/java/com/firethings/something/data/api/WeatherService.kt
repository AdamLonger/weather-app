package com.firethings.something.data.api

import com.firethings.something.data.api.model.WeatherModel
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("weather")
    suspend fun getWeather(
        @Query("lat") lat: Float,
        @Query("lon") lon: Float,
        @Query("units") unit: String,
        @Query("appid") apiKey: String,
    ): Response<WeatherModel?>
}
