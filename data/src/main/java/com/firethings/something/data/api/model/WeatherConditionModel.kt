package com.firethings.something.data.api.model

import kotlinx.serialization.Serializable

@Serializable
data class WeatherConditionModel(
    val id: Int? = null,
    val main: String? = null,
    val description: String? = null,
    val icon: String? = null
)
