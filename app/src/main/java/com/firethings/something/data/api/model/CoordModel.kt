package com.firethings.something.data.api.model

import kotlinx.serialization.Serializable

@Serializable
data class CoordModel(
    val lon: Float = 0f,
    val lat: Float = 0f
)
