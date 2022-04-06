package com.firethings.something.data.local.model

import androidx.room.ColumnInfo
import com.firethings.something.domain.model.Temperature

data class MainEntry(
    val temp: Temperature? = null,
    @ColumnInfo(name = "feels_like")
    val feelsLike: Temperature? = null,
    @ColumnInfo(name = "temp_min")
    val tempMin: Temperature? = null,
    @ColumnInfo(name = "temp_max")
    val tempMax: Temperature? = null,
    @ColumnInfo(name = "sea_level")
    val seaLevel: Int? = null,
    @ColumnInfo(name = "grnd_level")
    val groundLevel: Int? = null,
    val pressure: Int? = null,
    val humidity: Int? = null,
)
