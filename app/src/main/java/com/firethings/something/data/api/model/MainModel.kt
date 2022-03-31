package com.firethings.something.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MainModel(
    val temp: Float? = null,
    @SerialName("feels_like")
    val feelsLike: Float? = null,
    @SerialName("temp_min")
    val tempMin: Float? = null,
    @SerialName("temp_max")
    val tempMax: Float? = null,
    @SerialName("sea_level")
    val seaLevel: Int? = null,
    @SerialName("grnd_level")
    val groundLevel: Int? = null,
    val pressure: Int? = null,
    val humidity: Int? = null,
)
