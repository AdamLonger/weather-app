package com.firethings.something.domain.model

import java.util.Date

data class SysDetails(
    val type: Int? = null,
    val id: Int? = null,
    val message: Float? = null,
    val country: String? = null,
    val sunrise: Date? = null,
    val sunset: Date? = null,
)
