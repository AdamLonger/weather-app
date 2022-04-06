package com.firethings.something.domain.model

import androidx.room.ColumnInfo

data class Rain(
    val oneHour: Float? = null,
    val threeHour: Float? = null,
)
