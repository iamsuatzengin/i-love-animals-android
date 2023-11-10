package com.suatzengin.iloveanimals.ui.advertisement.adapter.viewholder

import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.suatzengin.iloveanimals.databinding.ItemAdCategoryBinding
import com.suatzengin.iloveanimals.domain.model.advertisement.AdvertisementCategory
import com.suatzengin.iloveanimals.ui.advertisement.callback.CategoryCallback

class CategoriesViewHolder(
    private val binding: ItemAdCategoryBinding,
    private val callback: CategoryCallback
) : ViewHolder(binding.root) {
    fun bind() {
        binding.rgAdCategory.setOnCheckedChangeListener { _, checkedId ->
            val rb = binding.rgAdCategory.findViewById<RadioButton>(checkedId)

            callback.onCategoryClick(
                category = AdvertisementCategory.getWithTitle(rb.text.toString())
            )
        }
    }
}
