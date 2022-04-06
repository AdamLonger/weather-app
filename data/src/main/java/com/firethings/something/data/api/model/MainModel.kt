package com.firethings.something.data.api.model

import com.google.gson.annotations.SerializedName

data class MainModel(
    val temp: Float? = null,
    @SerializedName("feels_like")
    val feelsLike: Float? = null,
    @SerializedName("temp_min")
    val tempMin: Float? = null,
    @SerializedName("temp_max")
    val tempMax: Float? = null,
    @SerializedName("sea_level")
    val seaLevel: Int? = null,
    @SerializedName("grnd_level")
    val groundLevel: Int? = null,
    val pressure: Int? = null,
    val humidity: Int? = null,
)
