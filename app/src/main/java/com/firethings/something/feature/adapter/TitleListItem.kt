package com.firethings.something.feature.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.firethings.something.R
import com.firethings.something.databinding.ItemTitleBinding
import com.mikepenz.fastadapter.binding.AbstractBindingItem

class TitleListItem(
    private val title: String,
    private val value: Any? = null
) : AbstractBindingItem<ItemTitleBinding>() {
    override val type: Int = R.id.item_title

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup?): ItemTitleBinding =
        ItemTitleBinding.inflate(inflater, parent, false)

    override fun bindView(binding: ItemTitleBinding, payloads: List<Any>) {
        binding.title.text = title
        binding.value.text = value?.toString() ?: ""
    }
}
