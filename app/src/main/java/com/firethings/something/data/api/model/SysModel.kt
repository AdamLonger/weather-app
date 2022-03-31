package com.firethings.something.data.api.model

import kotlinx.serialization.Serializable

@Serializable
data class SysModel(
    val type: Int? = null,
    val id: Int? = null,
    val message: Float? = null,
    val country: String? = null,
    val sunrise: Long? = null,
    val sunset: Long? = null,
)
