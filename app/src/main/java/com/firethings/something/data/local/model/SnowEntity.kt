package com.firethings.something.data.local.model

import androidx.room.ColumnInfo

data class SnowEntity(
    @ColumnInfo(name = "snow_1h")
    val oneHour: Float? = null,
    @ColumnInfo(name = "snow_3h")
    val threeHour: Float? = null,
)
