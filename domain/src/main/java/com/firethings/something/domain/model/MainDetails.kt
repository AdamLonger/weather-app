package com.firethings.something.domain.model

data class MainDetails(
    val temp: Temperature? = null,
    val feelsLike: Temperature? = null,
    val tempMin: Temperature? = null,
    val tempMax: Temperature? = null,
    val seaLevel: Int? = null,
    val groundLevel: Int? = null,
    val pressure: Int? = null,
    val humidity: Int? = null,
)
