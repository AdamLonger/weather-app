package com.firethings.something.data.mappers

import com.firethings.something.data.api.model.WeatherModel
import com.firethings.something.data.local.model.WeatherEntry
import com.firethings.something.domain.model.Weather
import java.util.*

fun WeatherModel.toDomain() = Weather(
    localId = null,
    coordinates = coord.toDomain(),
    conditions = weather?.map { it.toDomain() } ?: emptyList(),
    base = base,
    main = main?.toDomain(parameterUnit),
    visibility = visibility,
    wind = wind?.toDomain(),
    clouds = clouds?.toDomain(),
    rain = rain?.toDomain(),
    snow = snow?.toDomain(),
    dt = dt,
    sys = sys?.toDomain(),
    timezone = timezone,
    cityId = cityId,
    name = name,
    cod = cod,
    date = Date(timestamp),
    parameterUnit = parameterUnit
)

fun Weather.getBaseEntry() = WeatherEntry(
    id = localId ?: 0,
    coordinates = coordinates,
    base = base,
    main = main?.toEntry(),
    visibility = visibility,
    wind = wind?.toEntry(),
    clouds = clouds?.toEntry(),
    rain = rain?.toEntry(),
    snow = snow?.toEntry(),
    dt = dt,
    sys = sys?.toEntry(),
    timezone = timezone,
    cityId = cityId,
    name = name,
    cod = cod,
    date = date,
    parameterUnit = parameterUnit
)

fun Weather.getConditionEntryList() = conditions.map { condition -> condition.toEntry() }
