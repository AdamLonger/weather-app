package com.firethings.something.data.mappers

import com.firethings.something.data.api.model.WeatherModel
import com.firethings.something.data.local.model.WeatherWithConditions
import com.firethings.something.domain.model.Weather

fun WeatherWithConditions.toDomain() = Weather(
    localId = weather.id,
    coordinates = weather.coordinates,
    base = weather.base,
    main = weather.main?.toDomain(),
    visibility = weather.visibility,
    wind = weather.wind?.toDomain(),
    clouds = weather.clouds?.toDomain(),
    rain = weather.rain?.toDomain(),
    snow = weather.snow?.toDomain(),
    dt = weather.dt,
    sys = weather.sys?.toDomain(),
    timezone = weather.timezone,
    cityId = weather.cityId,
    name = weather.name,
    cod = weather.cod,
    date = weather.date,
    parameterUnit = weather.parameterUnit
)

fun WeatherModel.toConditionEntities() = weather?.map { it.toEntry() } ?: emptyList()
