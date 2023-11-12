package com.suatzengin.iloveanimals.ui.advertisement.adapter.model


data class AdRecyclerItem(
    val id: String,
    val creatorId: String,
    val title: String,
    val description: String,
    val images: List<String>,
    val address: String,
    val createdAt: String,
    val isImageSizeBiggerThan1: Boolean,
    val isImageSizeBiggerThan2: Boolean
) : RecyclerItem {
    override val type: Int = 3
}
