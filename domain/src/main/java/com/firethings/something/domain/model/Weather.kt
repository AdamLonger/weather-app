package com.firethings.something.domain.model

import java.util.Date

sealed class Weather {
    abstract val coordinates: Coordinates
    abstract val base: String?
    abstract val main: MainDetails?
    abstract val conditions: List<WeatherCondition>
    abstract val visibility: Int?
    abstract val wind: Wind?
    abstract val clouds: Cloud?
    abstract val rain: Rain?
    abstract val snow: Snow?
    abstract val dt: Int?
    abstract val sys: SysDetails?
    abstract val timezone: Int?
    abstract val cityId: Int?
    abstract val name: String?
    abstract val cod: Int?
    abstract val date: Date
    abstract val parameterUnit: ParameterUnit

    data class Stored(
        val localId: Long,
        override val coordinates: Coordinates,
        override val base: String? = null,
        override val main: MainDetails? = null,
        override val conditions: List<WeatherCondition.Stored> = emptyList(),
        override val visibility: Int? = null,
        override val wind: Wind? = null,
        override val clouds: Cloud? = null,
        override val rain: Rain? = null,
        override val snow: Snow? = null,
        override val dt: Int? = null,
        override val sys: SysDetails? = null,
        override val timezone: Int? = null,
        override val cityId: Int? = null,
        override val name: String? = null,
        override val cod: Int? = null,
        override val date: Date,
        override val parameterUnit: ParameterUnit
    ) : Weather()

    data class Simple(
        override val coordinates: Coordinates,
        override val base: String? = null,
        override val main: MainDetails? = null,
        override val conditions: List<WeatherCondition> = emptyList(),
        override val visibility: Int? = null,
        override val wind: Wind? = null,
        override val clouds: Cloud? = null,
        override val rain: Rain? = null,
        override val snow: Snow? = null,
        override val dt: Int? = null,
        override val sys: SysDetails? = null,
        override val timezone: Int? = null,
        override val cityId: Int? = null,
        override val name: String? = null,
        override val cod: Int? = null,
        override val date: Date,
        override val parameterUnit: ParameterUnit
    ) : Weather()
}
