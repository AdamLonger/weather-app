package com.firethings.something.weather.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.firethings.something.common.Formatters
import com.firethings.something.domain.model.Weather
import com.firethings.something.weather.R
import com.firethings.something.weather.databinding.ItemWeatherBinding
import com.mikepenz.fastadapter.binding.AbstractBindingItem

class WeatherItem(
    val data: Weather
) : AbstractBindingItem<ItemWeatherBinding>() {
    override val type: Int = R.id.item_weather
    override var identifier: Long = data.localId?.toLong() ?: 0

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup?): ItemWeatherBinding =
        ItemWeatherBinding.inflate(inflater, parent, false)

    override fun bindView(binding: ItemWeatherBinding, payloads: List<Any>) {
        binding.temperature.text = data.main?.temp?.formatted ?: binding.root.context.getString(R.string.unknown)

        binding.date.text = Formatters.dateTimeFormat.format(data.date)
    }
}
