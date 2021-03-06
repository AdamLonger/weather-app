package com.firethings.something.domain.usecase

import com.firethings.something.domain.model.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherStorageUseCase {
    fun weatherFlow(): Flow<List<Weather.Stored>>
    suspend fun weatherById(id: Long): Weather.Stored?
    suspend fun insertWeatherAndConditions(weather: Weather)
    suspend fun updateWeather(weather: Weather)
    suspend fun deleteById(id: Long)
}
