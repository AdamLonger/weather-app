package com.firethings.something.feature.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.firethings.something.R
import com.firethings.something.common.Formatters
import com.firethings.something.data.local.model.WeatherEntity
import com.firethings.something.databinding.ItemWeatherBinding
import com.mikepenz.fastadapter.binding.AbstractBindingItem

class WeatherItem(
    val data: WeatherEntity
) : AbstractBindingItem<ItemWeatherBinding>() {
    override val type: Int = R.id.item_weather
    override var identifier: Long = data.id.toLong()

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup?): ItemWeatherBinding =
        ItemWeatherBinding.inflate(inflater, parent, false)

    override fun bindView(binding: ItemWeatherBinding, payloads: List<Any>) {
        binding.temperature.text = data.main?.temp?.formatted ?: binding.root.context.getString(R.string.unknown)

        binding.date.text = Formatters.dateTimeFormat.format(data.date)
    }
}
