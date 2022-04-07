package com.firethings.something.domain.usecase

import com.firethings.something.domain.model.Coordinates
import com.firethings.something.domain.model.ParameterUnit
import com.firethings.something.domain.model.Weather

interface WeatherUseCase {
    suspend fun getWeather(location: Coordinates, unit: ParameterUnit = ParameterUnit.Metric): Weather.Simple?
}
