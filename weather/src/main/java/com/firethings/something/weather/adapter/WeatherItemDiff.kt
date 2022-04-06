package com.firethings.something.weather.adapter

import com.mikepenz.fastadapter.diff.DiffCallback

object WeatherItemDiff : DiffCallback<WeatherItem> {
    override fun areContentsTheSame(oldItem: WeatherItem, newItem: WeatherItem): Boolean =
        oldItem.data == newItem.data

    override fun areItemsTheSame(oldItem: WeatherItem, newItem: WeatherItem): Boolean =
        oldItem.data.localId ?: 0 == newItem.data.localId ?: 0

    override fun getChangePayload(
        oldItem: WeatherItem,
        oldItemPosition: Int,
        newItem: WeatherItem,
        newItemPosition: Int
    ): Any = DummyPayload
}

private object DummyPayload
