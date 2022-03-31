package com.firethings.something.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_condition_table")
data class WeatherConditionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "parent_id")
    val parentId: Int = 0,
    @ColumnInfo(name = "api_id")
    val apiId: Int? = null,
    val main: String? = null,
    val description: String? = null,
    val icon: String? = null
)
