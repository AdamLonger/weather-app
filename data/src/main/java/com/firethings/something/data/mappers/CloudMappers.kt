package com.firethings.something.data.mappers

import com.firethings.something.data.api.model.CloudModel
import com.firethings.something.data.local.model.CloudEntry
import com.firethings.something.domain.model.Cloud

fun CloudEntry.toDomain() = Cloud(
    all = all
)

fun CloudModel.toDomain() = Cloud(
    all = all
)

fun CloudModel.toEntry() = CloudEntry(
    all = all
)

fun Cloud.toEntry() = CloudEntry(
    all = all
)
