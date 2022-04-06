package com.firethings.something.data.mappers

import com.firethings.something.data.api.model.SysModel
import com.firethings.something.data.local.model.SysEntry
import com.firethings.something.domain.model.SysDetails
import java.util.Date

fun SysEntry.toDomain() = SysDetails(
    type = type,
    id = id,
    message = message,
    country = country,
    sunrise = sunrise,
    sunset = sunset,
)

fun SysModel.toDomain() = SysDetails(
    type = type,
    id = id,
    message = message,
    country = country,
    sunrise = sunrise?.let { Date(it) },
    sunset = sunset?.let { Date(it) },
)

fun SysModel.toEntry() = SysEntry(
    type = type,
    id = id,
    message = message,
    country = country,
    sunrise = sunrise?.let { Date(it) },
    sunset = sunset?.let { Date(it) },
)

fun SysDetails.toEntry() = SysEntry(
    type = type,
    id = id,
    message = message,
    country = country,
    sunrise = sunrise,
    sunset = sunset,
)
