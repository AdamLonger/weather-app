package com.firethings.something.data.local.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.firethings.something.domain.model.Coordinates
import com.firethings.something.domain.model.ParameterUnit
import java.util.Date

@Entity(tableName = "weather_table")
data class WeatherEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @Embedded
    val coordinates: Coordinates,
    val base: String? = null,
    @Embedded
    val main: MainEntry? = null,
    val visibility: Int? = null,
    @Embedded
    val wind: WindEntry? = null,
    @Embedded
    val clouds: CloudEntry? = null,
    @Embedded
    val rain: RainEntry? = null,
    @Embedded
    val snow: SnowEntry? = null,
    val dt: Int? = null,
    @Embedded
    val sys: SysEntry? = null,
    val timezone: Int? = null,
    val cityId: Int? = null,
    val name: String? = null,
    val cod: Int? = null,
    val date: Date,
    val parameterUnit: ParameterUnit
)
