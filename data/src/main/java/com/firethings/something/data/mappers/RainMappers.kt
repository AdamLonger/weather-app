package com.firethings.something.data.mappers

import com.firethings.something.data.api.model.RainModel
import com.firethings.something.data.local.model.RainEntry
import com.firethings.something.domain.model.Rain

fun RainEntry.toDomain() = Rain(
    oneHour = oneHour,
    threeHour = threeHour
)

fun RainModel.toDomain() = Rain(
    oneHour = oneHour,
    threeHour = threeHour
)

fun RainModel.toEntry() = RainEntry(
    oneHour = oneHour,
    threeHour = threeHour
)

fun Rain.toEntry() = RainEntry(
    oneHour = oneHour,
    threeHour = threeHour
)
