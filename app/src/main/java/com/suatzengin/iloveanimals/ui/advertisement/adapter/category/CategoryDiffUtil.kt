package com.suatzengin.iloveanimals.ui.advertisement.adapter.category

import androidx.recyclerview.widget.DiffUtil
import com.suatzengin.iloveanimals.domain.model.advertisement.AdvertisementCategory

class CategoryDiffUtil: DiffUtil.ItemCallback<AdvertisementCategory>() {
    override fun areItemsTheSame(
        oldItem: AdvertisementCategory,
        newItem: AdvertisementCategory
    ): Boolean {
        return oldItem == newItem
    }
    override fun areContentsTheSame(
        oldItem: AdvertisementCategory,
        newItem: AdvertisementCategory
    ): Boolean {
        return oldItem == newItem
    }
}
