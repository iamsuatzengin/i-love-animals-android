package com.suatzengin.iloveanimals.ui.advertisement.adapter.category

import androidx.recyclerview.widget.RecyclerView
import com.suatzengin.iloveanimals.databinding.ItemCategoryListBinding
import com.suatzengin.iloveanimals.domain.model.advertisement.AdvertisementCategory
import com.suatzengin.iloveanimals.ui.advertisement.callback.CategoryCallback

class CategoryViewHolder(
    private val binding: ItemCategoryListBinding,
    private val callback: CategoryCallback
): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: AdvertisementCategory) {
        binding.apply {
            tvCategoryTitle.text = AdvertisementCategory.getWithTitle(item)

            root.setOnClickListener {
                callback.onCategoryClick(category = item)
            }
        }
    }
}
