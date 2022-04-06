package com.firethings.something.data.mappers

import com.firethings.something.data.api.model.CoordinatesModel
import com.firethings.something.domain.model.Coordinates

fun CoordinatesModel.toDomain() = Coordinates(
    lat = lat,
    lon = lon
)
