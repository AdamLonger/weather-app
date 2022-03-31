package com.firethings.something.data.local.model

import androidx.room.ColumnInfo

data class RainEntity(
    @ColumnInfo(name = "rain_1h")
    val oneHour: Float? = null,
    @ColumnInfo(name = "rain_3h")
    val threeHour: Float? = null,
)
