package com.firethings.something.data.local.model

import androidx.room.Embedded
import androidx.room.Relation

data class WeatherWithConditions(
    @Embedded val weather: WeatherEntry,
    @Relation(
        parentColumn = "id",
        entityColumn = "parent_id"
    )
    val weatherCondition: List<WeatherConditionEntry> = emptyList(),
)
