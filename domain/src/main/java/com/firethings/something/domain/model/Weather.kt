package com.firethings.something.domain.model

import java.util.Date

data class Weather(
    val localId: Int?,
    val coordinates: Coordinates,
    val base: String? = null,
    val main: MainDetails? = null,
    val conditions: List<WeatherCondition> = emptyList(),
    val visibility: Int? = null,
    val wind: Wind? = null,
    val clouds: Cloud? = null,
    val rain: Rain? = null,
    val snow: Snow? = null,
    val dt: Int? = null,
    val sys: SysDetails? = null,
    val timezone: Int? = null,
    val cityId: Int? = null,
    val name: String? = null,
    val cod: Int? = null,
    val date: Date,
    val parameterUnit: ParameterUnit
)
