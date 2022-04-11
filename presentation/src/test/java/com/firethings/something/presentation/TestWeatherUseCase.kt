package com.firethings.something.presentation

import com.firethings.something.domain.model.Coordinates
import com.firethings.something.domain.model.MainDetails
import com.firethings.something.domain.model.ParameterUnit
import com.firethings.something.domain.model.Temperature
import com.firethings.something.domain.model.Weather
import com.firethings.something.domain.usecase.WeatherUseCase
import java.util.Date

class TestWeatherUseCase : WeatherUseCase {

    override suspend fun getWeather(location: Coordinates, unit: ParameterUnit): Weather.Simple = testWeather

    @Suppress("MagicNumber")
    companion object {
        val testWeather: Weather.Simple = Weather.Simple(
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
