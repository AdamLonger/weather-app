package com.firethings.something.feature.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.firethings.something.R
import com.firethings.something.databinding.ItemPropertyBinding
import com.mikepenz.fastadapter.binding.AbstractBindingItem

class PropertyListItem(
    private val name: String,
    private val value: Any?
) : AbstractBindingItem<ItemPropertyBinding>() {
    override val type: Int = R.id.item_property

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup?): ItemPropertyBinding =
        ItemPropertyBinding.inflate(inflater, parent, false)

    override fun bindView(binding: ItemPropertyBinding, payloads: List<Any>) {
        binding.name.text = name
        binding.value.text = value?.toString()
    }
}
