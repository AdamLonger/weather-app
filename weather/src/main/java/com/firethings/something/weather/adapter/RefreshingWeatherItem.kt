package com.firethings.something.weather.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.firethings.something.common.Formatters
import com.firethings.something.domain.model.Weather
import com.firethings.something.weather.R
import com.firethings.something.weather.databinding.ItemWeatherBinding
import com.firethings.something.weather.databinding.ItemWeatherRefreshingBinding
import com.mikepenz.fastadapter.binding.AbstractBindingItem

class RefreshingWeatherItem(
    override var identifier: Long,
    val data: Weather.Simple
) : AbstractBindingItem<ItemWeatherRefreshingBinding>() {
    override val type: Int = R.id.item_weather_refreshing

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup?): ItemWeatherRefreshingBinding =
        ItemWeatherRefreshingBinding.inflate(inflater, parent, false)

    override fun bindView(binding: ItemWeatherRefreshingBinding, payloads: List<Any>) {
        binding.temperature.text = data.main?.temp?.formatted ?: binding.root.context.getString(R.string.unknown)
        binding.date.text = Formatters.dateTimeFormat.format(data.date)
        binding.coordinates.text = data.coordinates.let { it.lat.toString() + " : " + it.lon }
    }
}
