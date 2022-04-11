package com.firethings.something.presentation

import com.firethings.something.domain.model.Coordinates
import com.firethings.something.domain.model.MainDetails
import com.firethings.something.domain.model.ParameterUnit
import com.firethings.something.domain.model.Temperature
import com.firethings.something.domain.model.Weather
import com.firethings.something.domain.usecase.WeatherStorageUseCase
import java.util.Date
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class TestWeatherStorageUseCase : WeatherStorageUseCase {
    override fun weatherFlow(): Flow<List<Weather.Stored>> = flowOf(listOf(testWeather))

    override suspend fun weatherById(id: Long): Weather.Stored = testWeather

    override suspend fun insertWeatherAndConditions(weather: Weather) = Unit

    override suspend fun updateWeather(weather: Weather) = Unit

    override suspend fun deleteById(id: Long) = Unit

    @Suppress("MagicNumber")
    companion object {
        val testWeather: Weather.Stored = Weather.Stored(
            localId = 10,
            coordinates = Coordinates(10f, 10f),
            base = "TestBase",
            main = MainDetails(
                temp = Temperature.Celsius(23f),
                tempMin = Temperature.Celsius(20f),
                tempMax = Temperature.Celsius(25f),
            ),
            date = Date(),
            parameterUnit = ParameterUnit.Metric
        )
    }
}
