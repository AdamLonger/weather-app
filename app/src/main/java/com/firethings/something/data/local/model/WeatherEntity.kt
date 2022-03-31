package com.firethings.something.data.local.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.firethings.something.data.api.model.ParameterUnit
import java.util.Date

@Entity(tableName = "weather_table")
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @Embedded
    val coord: CoordEntity,
    val base: String? = null,
    @Embedded
    val main: MainEntity? = null,
    val visibility: Int? = null,
    @Embedded
    val wind: WindEntity? = null,
    @Embedded
    val clouds: CloudEntity? = null,
    @Embedded
    val rain: RainEntity? = null,
    @Embedded
    val snow: SnowEntity? = null,
    val dt: Int? = null,
    @Embedded
    val sys: SysEntity? = null,
    val timezone: Int? = null,
    val cityId: Int? = null,
    val name: String? = null,
    val cod: Int? = null,
    val date: Date,
    val parameterUnit: ParameterUnit
)
