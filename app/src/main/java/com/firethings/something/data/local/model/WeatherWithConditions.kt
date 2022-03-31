package com.firethings.something.data.local.model

import androidx.room.Embedded
import androidx.room.Relation

data class WeatherWithConditions(
    @Embedded val weatherEntity: WeatherEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "parent_id"
    )
    val weatherCondition: List<WeatherConditionEntity> = emptyList(),
)
