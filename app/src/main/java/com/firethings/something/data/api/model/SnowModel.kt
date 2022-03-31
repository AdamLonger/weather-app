package com.firethings.something.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SnowModel(
    @SerialName("1h")
    val oneHour: Float? = null,
    @SerialName("3h")
    val threeHour: Float? = null,
)
