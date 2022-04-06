package com.firethings.something.domain.usecase

import com.firethings.something.domain.model.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherStorageUseCase {
    fun weatherFlow(): Flow<List<Weather>>
    suspend fun weatherById(id: Int): Weather?
    suspend fun insertWeatherAndConditions(weather: Weather)
    suspend fun deleteById(id: Int)
}
