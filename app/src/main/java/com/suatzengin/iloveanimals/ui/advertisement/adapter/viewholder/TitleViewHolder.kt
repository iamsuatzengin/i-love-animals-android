package com.suatzengin.iloveanimals.ui.advertisement.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.suatzengin.iloveanimals.databinding.ItemTitleLabelBinding
import com.suatzengin.iloveanimals.ui.advertisement.adapter.model.TitleRecyclerItem

class TitleViewHolder(
    private val binding: ItemTitleLabelBinding
) : ViewHolder(binding.root){
    fun bind(item: TitleRecyclerItem) {
        binding.tvCategory.text = item.title
    }
}
