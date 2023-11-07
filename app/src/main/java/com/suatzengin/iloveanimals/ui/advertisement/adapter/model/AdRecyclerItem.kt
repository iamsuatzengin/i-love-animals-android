package com.suatzengin.iloveanimals.ui.advertisement.adapter.model


data class AdRecyclerItem(
    val id: String,
    val creatorId: String,
    val title: String,
    val description: String,
    val images: List<String>,
    val address: String,
    val createdAt: String
): RecyclerItem {
    override val type: Int = 3

    val isImageSizeBiggerThan1: Boolean = images.size > 1
    val isImageSizeBiggerThan2: Boolean = images.size > 2
}
