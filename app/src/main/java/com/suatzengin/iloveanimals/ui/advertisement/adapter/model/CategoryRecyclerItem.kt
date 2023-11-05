package com.suatzengin.iloveanimals.ui.advertisement.adapter.model

import com.suatzengin.iloveanimals.domain.model.advertisement.AdvertisementCategory

data class CategoryRecyclerItem(
    val categories: List<AdvertisementCategory>
): RecyclerItem{
    override val type: Int = 2
}
