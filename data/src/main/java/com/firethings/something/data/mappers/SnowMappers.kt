package com.firethings.something.data.mappers

import com.firethings.something.data.api.model.SnowModel
import com.firethings.something.data.local.model.SnowEntry
import com.firethings.something.domain.model.Snow

fun SnowModel.toDomain() = Snow(
    oneHour = oneHour,
    threeHour = threeHour
)

fun SnowEntry.toDomain() = Snow(
    oneHour = oneHour,
    threeHour = threeHour
)

fun SnowModel.toEntry() = SnowEntry(
    oneHour = oneHour,
    threeHour = threeHour
)

fun Snow.toEntry() = SnowEntry(
    oneHour = oneHour,
    threeHour = threeHour
)
