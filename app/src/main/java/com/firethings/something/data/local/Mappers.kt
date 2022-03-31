package com.firethings.something.data.local

import com.firethings.something.data.api.model.CloudModel
import com.firethings.something.data.api.model.CoordModel
import com.firethings.something.data.api.model.MainModel
import com.firethings.something.data.api.model.ParameterUnit
import com.firethings.something.data.api.model.RainModel
import com.firethings.something.data.api.model.SnowModel
import com.firethings.something.data.api.model.SysModel
import com.firethings.something.data.api.model.WeatherConditionModel
import com.firethings.something.data.api.model.WeatherModel
import com.firethings.something.data.api.model.WindModel
import com.firethings.something.data.local.model.CloudEntity
import com.firethings.something.data.local.model.CoordEntity
import com.firethings.something.data.local.model.MainEntity
import com.firethings.something.data.local.model.RainEntity
import com.firethings.something.data.local.model.SnowEntity
import com.firethings.something.data.local.model.SysEntity
import com.firethings.something.data.local.model.Temperature
import com.firethings.something.data.local.model.WeatherConditionEntity
import com.firethings.something.data.local.model.WeatherEntity
import com.firethings.something.data.local.model.WeatherWithConditions
import com.firethings.something.data.local.model.WindEntity
import java.util.Date

fun WeatherModel.toWeatherWithConditions() = WeatherWithConditions(
    weatherEntity = this.toEntity(),
    weatherCondition = this.toConditionEntities()
)

fun WeatherModel.toEntity() = WeatherEntity(
    coord = coord.toEntity(),
    base = base,
    main = main?.toEntity(parameterUnit),
    visibility = visibility,
    wind = wind?.toEntity(),
    clouds = clouds?.toEntity(),
    rain = rain?.toEntity(),
    snow = snow?.toEntity(),
    dt = dt,
    sys = sys?.toEntity(),
    timezone = timezone,
    cityId = cityId,
    name = name,
    cod = cod,
    date = Date(timestamp),
    parameterUnit = parameterUnit
)

fun WeatherModel.toConditionEntities() = weather?.map { it.toEntity() } ?: emptyList()

fun CoordModel.toEntity() = CoordEntity(
    lat = lat,
    lon = lon
)

fun WeatherConditionModel.toEntity() = WeatherConditionEntity(
    apiId = id,
    main = main,
    description = description,
    icon = icon
)

fun MainModel.toEntity(unit: ParameterUnit) = MainEntity(
    temp = temp?.let { Temperature.fromValueAndUnit(it, unit) },
    feelsLike = feelsLike?.let { Temperature.fromValueAndUnit(it, unit) },
    tempMin = tempMin?.let { Temperature.fromValueAndUnit(it, unit) },
    tempMax = tempMax?.let { Temperature.fromValueAndUnit(it, unit) },
    seaLevel = seaLevel,
    groundLevel = groundLevel,
    pressure = pressure,
    humidity = humidity
)

fun WindModel.toEntity() = WindEntity(
    speed = speed,
    deg = deg,
    gust = gust
)

fun CloudModel.toEntity() = CloudEntity(
    all = all
)

fun RainModel.toEntity() = RainEntity(
    oneHour = oneHour,
    threeHour = threeHour
)

fun SnowModel.toEntity() = SnowEntity(
    oneHour = oneHour,
    threeHour = threeHour
)

fun SysModel.toEntity() = SysEntity(
    type = type,
    id = id,
    message = message,
    country = country,
    sunrise = sunrise?.let { Date(it) },
    sunset = sunset?.let { Date(it) },
)
