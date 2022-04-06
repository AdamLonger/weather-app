package com.firethings.something.data.local.model

import androidx.room.ColumnInfo

data class WindEntry(
    @ColumnInfo(name = "wind_speed")
    val speed: Float? = null,
    @ColumnInfo(name = "wind_deg")
    val deg: Float? = null,
    @ColumnInfo(name = "wind_gust")
    val gust: Float? = null,
)
