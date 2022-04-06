package com.firethings.something.data.api.model

import kotlinx.serialization.Serializable

@Serializable
data class WindModel(
    val speed: Float? = null,
    val deg: Float? = null,
    val gust: Float? = null,
)
