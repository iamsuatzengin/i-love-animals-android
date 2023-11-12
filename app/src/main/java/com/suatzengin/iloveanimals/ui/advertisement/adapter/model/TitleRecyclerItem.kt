package com.suatzengin.iloveanimals.ui.advertisement.adapter.model

data class TitleRecyclerItem(
    val title: String
): RecyclerItem {
    override val type: Int
        get() = 1
}
