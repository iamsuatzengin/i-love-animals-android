package com.suatzengin.iloveanimals.ui.advertisement

import com.suatzengin.iloveanimals.domain.model.advertisement.Advertisement
import com.suatzengin.iloveanimals.domain.model.advertisement.AdvertisementCategory
import com.suatzengin.iloveanimals.ui.advertisement.adapter.model.AdRecyclerItem
import com.suatzengin.iloveanimals.ui.advertisement.adapter.model.CategoryRecyclerItem
import com.suatzengin.iloveanimals.ui.advertisement.adapter.model.RecyclerItem
import com.suatzengin.iloveanimals.ui.advertisement.adapter.model.TitleRecyclerItem
import com.suatzengin.iloveanimals.ui.advertisement.adapter.model.TopRecyclerItem

data class AdvertisementUiState(
    val advertisementList: List<Advertisement> = emptyList(),
    val selectedCategory: AdvertisementCategory = AdvertisementCategory.ALL,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
) {
    val recyclerItems: List<RecyclerItem>
        get() = fillRecyclerItemList()
}

private fun AdvertisementUiState.fillRecyclerItemList(): List<RecyclerItem> {
    val list = arrayListOf<RecyclerItem>()

    val adRecyclerList = advertisementList.map { advertisement ->
        AdRecyclerItem(
            id = advertisement.id,
            creatorId = advertisement.creatorId,
            title = advertisement.title,
            description = advertisement.description,
            images = advertisement.images,
            address = advertisement.location.address,
            createdAt = advertisement.createdAt,
            isImageSizeBiggerThan1 = advertisement.isImageSizeBiggerThan1,
            isImageSizeBiggerThan2 = advertisement.isImageSizeBiggerThan2
        )
    }

    list.add(TopRecyclerItem())
    list.add(TitleRecyclerItem(title = "Kategori"))
    list.add(CategoryRecyclerItem(selectedCategory))
    list.addAll(adRecyclerList)

    return list
}