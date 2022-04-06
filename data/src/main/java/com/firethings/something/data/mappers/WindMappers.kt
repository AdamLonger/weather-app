package com.firethings.something.data.mappers

import com.firethings.something.data.api.model.WindModel
import com.firethings.something.data.local.model.WindEntry
import com.firethings.something.domain.model.Wind

fun WindEntry.toDomain() = Wind(
    speed = speed,
    deg = deg,
    gust = gust
)

fun WindModel.toDomain() = Wind(
    speed = speed,
    deg = deg,
    gust = gust
)

fun WindModel.toEntry() = WindEntry(
    speed = speed,
    deg = deg,
    gust = gust
)

fun Wind.toEntry() = WindEntry(
    speed = speed,
    deg = deg,
    gust = gust
)
