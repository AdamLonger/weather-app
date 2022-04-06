package com.firethings.something.domain.model

import androidx.room.ColumnInfo
import com.firethings.something.domain.model.Temperature

data class MainDetails(
    val temp: Temperature? = null,
    val feelsLike: Temperature? = null,
    val tempMin: Temperature? = null,
    val tempMax: Temperature? = null,
    val seaLevel: Int? = null,
    val groundLevel: Int? = null,
    val pressure: Int? = null,
    val humidity: Int? = null,
)
