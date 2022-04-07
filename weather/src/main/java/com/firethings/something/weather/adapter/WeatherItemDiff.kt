package com.firethings.something.weather.adapter

import com.mikepenz.fastadapter.GenericItem
import com.mikepenz.fastadapter.diff.DiffCallback

object WeatherItemDiff : DiffCallback<GenericItem> {
    override fun areContentsTheSame(oldItem: GenericItem, newItem: GenericItem): Boolean = when {
        oldItem is WeatherItem && newItem is WeatherItem -> oldItem.data == newItem.data
        else -> oldItem.identifier == newItem.identifier
    }

    override fun areItemsTheSame(oldItem: GenericItem, newItem: GenericItem): Boolean =
        oldItem.identifier == newItem.identifier

    override fun getChangePayload(
        oldItem: GenericItem,
        oldItemPosition: Int,
        newItem: GenericItem,
        newItemPosition: Int
    ): Any = 0
}

internal object DummyPayload
