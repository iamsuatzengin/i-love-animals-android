package com.suatzengin.iloveanimals.ui.advertisement.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.suatzengin.iloveanimals.databinding.ItemAdCategoryBinding
import com.suatzengin.iloveanimals.domain.model.advertisement.AdvertisementCategory
import com.suatzengin.iloveanimals.ui.advertisement.adapter.model.CategoryRecyclerItem
import com.suatzengin.iloveanimals.ui.advertisement.callback.CategoryCallback

class CategoriesViewHolder(
    private val binding: ItemAdCategoryBinding,
    private val callback: CategoryCallback
) : ViewHolder(binding.root) {
    fun bind(item: CategoryRecyclerItem) = with(binding) {

        AdvertisementCategory.entries.forEach { category ->
            item.setState(root.resources, getCategoryTextView(category), category)
        }

        tvAll.setOnClickListener {
            callback.onCategoryClick(category = AdvertisementCategory.ALL)
        }
        tvInjured.setOnClickListener {
            callback.onCategoryClick(category = AdvertisementCategory.INJURED)
        }
        tvFeedSupport.setOnClickListener {
            callback.onCategoryClick(category = AdvertisementCategory.FEED_SUPPORT)
        }
        tvAdopt.setOnClickListener {
            callback.onCategoryClick(category = AdvertisementCategory.ADOPT)
        }
    }

    private fun getCategoryTextView(category: AdvertisementCategory) = when(category) {
        AdvertisementCategory.ALL -> binding.tvAll
        AdvertisementCategory.INJURED -> binding.tvInjured
        AdvertisementCategory.FEED_SUPPORT -> binding.tvFeedSupport
        AdvertisementCategory.ADOPT -> binding.tvAdopt
    }
}
