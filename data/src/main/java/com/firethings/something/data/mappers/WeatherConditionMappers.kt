package com.firethings.something.data.mappers

import com.firethings.something.data.api.model.WeatherConditionModel
import com.firethings.something.data.local.model.WeatherConditionEntry
import com.firethings.something.domain.model.WeatherCondition

fun WeatherConditionEntry.toDomain() = WeatherCondition(
    localId = id,
    localParentId = parentId,
    apiId = id,
    main = main,
    description = description,
    icon = icon
)

fun WeatherConditionModel.toDomain() = WeatherCondition(
    localId = null,
    localParentId = null,
    apiId = id,
    main = main,
    description = description,
    icon = icon
)

fun WeatherConditionModel.toEntry() = WeatherConditionEntry(
    apiId = id,
    main = main,
    description = description,
    icon = icon
)

fun WeatherCondition.toEntry() = WeatherConditionEntry(
    apiId = apiId,
    main = main,
    description = description,
    icon = icon
)
