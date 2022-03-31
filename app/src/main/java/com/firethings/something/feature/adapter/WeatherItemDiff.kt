package com.firethings.something.feature.adapter

import com.mikepenz.fastadapter.diff.DiffCallback

object WeatherItemDiff : DiffCallback<WeatherItem> {
    override fun areContentsTheSame(oldItem: WeatherItem, newItem: WeatherItem): Boolean =
        oldItem.data == newItem.data

    override fun areItemsTheSame(oldItem: WeatherItem, newItem: WeatherItem): Boolean =
        oldItem.data.id == newItem.data.id

    override fun getChangePayload(
        oldItem: WeatherItem,
        oldItemPosition: Int,
        newItem: WeatherItem,
        newItemPosition: Int
    ): Any = DummyPayload
}

private object DummyPayload
