package com.firethings.something.data.mappers

import com.firethings.something.data.api.model.WeatherConditionModel
import com.firethings.something.data.local.model.WeatherConditionEntry
import com.firethings.something.domain.model.WeatherCondition

fun WeatherConditionEntry.toDomain() = WeatherCondition.Stored(
    localId = id,
    localParentId = parentId,
    apiId = id,
    main = main,
    description = description,
    icon = icon
)

fun WeatherConditionModel.toDomain() = WeatherCondition.Simple(
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
    id = if (this is WeatherCondition.Stored) this.localId else 0,
    parentId = if (this is WeatherCondition.Stored) this.localParentId else 0,
    apiId = apiId,
    main = main,
    description = description,
    icon = icon
)
