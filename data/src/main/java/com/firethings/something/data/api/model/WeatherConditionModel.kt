package com.firethings.something.data.api.model

data class WeatherConditionModel(
    val id: Long,
    val main: String? = null,
    val description: String? = null,
    val icon: String? = null
)
