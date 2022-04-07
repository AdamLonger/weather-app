package com.firethings.something.data.usecase

import com.firethings.something.data.api.WeatherClient
import com.firethings.something.domain.model.ParameterUnit
import com.firethings.something.data.mappers.toDomain
import com.firethings.something.domain.model.Coordinates
import com.firethings.something.domain.model.Weather
import com.firethings.something.domain.usecase.WeatherUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DefaultWeatherUseCase(
    private val weatherClient: WeatherClient
) : WeatherUseCase {
    override suspend fun getWeather(location: Coordinates, unit: ParameterUnit): Weather.Simple? =
        withContext(Dispatchers.IO) {
            weatherClient.getWeather(location, unit)?.toDomain()
        }
}
