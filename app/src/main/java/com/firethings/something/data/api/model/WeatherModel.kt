package com.firethings.something.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherModel(
    val coord: CoordModel,
    val weather: List<WeatherConditionModel>? = null,
    val base: String? = null,
    val main: MainModel? = null,
    val visibility: Int? = null,
    val wind: WindModel? = null,
    val clouds: CloudModel? = null,
    val rain: RainModel? = null,
    val snow: SnowModel? = null,
    val dt: Int? = null,
    val sys: SysModel? = null,
    val timezone: Int? = null,
    @SerialName("id")
    val cityId: Int? = null,
    val name: String? = null,
    val cod: Int? = null,
    val timestamp: Long = 0,
    val parameterUnit: ParameterUnit = ParameterUnit.DEFAULT
)
