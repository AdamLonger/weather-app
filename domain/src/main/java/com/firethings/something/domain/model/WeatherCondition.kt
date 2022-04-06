package com.firethings.something.domain.model

data class WeatherCondition(
    val localId: Int?,
    val localParentId: Int?,
    val apiId: Int? = null,
    val main: String? = null,
    val description: String? = null,
    val icon: String? = null
)
