package com.firethings.something.data.mappers

import com.firethings.something.data.api.model.MainModel
import com.firethings.something.data.local.model.MainEntry
import com.firethings.something.domain.model.MainDetails
import com.firethings.something.domain.model.ParameterUnit
import com.firethings.something.domain.model.Temperature

fun MainEntry.toDomain() = MainDetails(
    temp = temp,
    feelsLike = feelsLike,
    tempMin = tempMin,
    tempMax = tempMax,
    seaLevel = seaLevel,
    groundLevel = groundLevel,
    pressure = pressure,
    humidity = humidity
)

fun MainModel.toDomain(unit: ParameterUnit) = MainDetails(
    temp = temp?.let { Temperature.fromValueAndUnit(it, unit) },
    feelsLike = feelsLike?.let { Temperature.fromValueAndUnit(it, unit) },
    tempMin = tempMin?.let { Temperature.fromValueAndUnit(it, unit) },
    tempMax = tempMax?.let { Temperature.fromValueAndUnit(it, unit) },
    seaLevel = seaLevel,
    groundLevel = groundLevel,
    pressure = pressure,
    humidity = humidity
)

fun MainModel.toEntry(unit: ParameterUnit) = MainEntry(
    temp = temp?.let { Temperature.fromValueAndUnit(it, unit) },
    feelsLike = feelsLike?.let { Temperature.fromValueAndUnit(it, unit) },
    tempMin = tempMin?.let { Temperature.fromValueAndUnit(it, unit) },
    tempMax = tempMax?.let { Temperature.fromValueAndUnit(it, unit) },
    seaLevel = seaLevel,
    groundLevel = groundLevel,
    pressure = pressure,
    humidity = humidity
)

fun MainDetails.toEntry() = MainEntry(
    temp = temp,
    feelsLike = feelsLike,
    tempMin = tempMin,
    tempMax = tempMax,
    seaLevel = seaLevel,
    groundLevel = groundLevel,
    pressure = pressure,
    humidity = humidity
)
