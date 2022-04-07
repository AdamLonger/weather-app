package com.firethings.something.data.api.model

import com.firethings.something.domain.model.ParameterUnit
import com.google.gson.annotations.SerializedName

data class WeatherAsdModel(
    val coord: CoordinatesModel,
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
    @SerializedName("id")
    val cityId: Int? = null,
    val name: String? = null,
    val cod: Int? = null,
    val timestamp: Long = 0,
    @Transient
    val parameterUnit: ParameterUnit = ParameterUnit.Metric
)
