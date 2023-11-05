package com.suatzengin.iloveanimals.ui.advertisement.adapter

import androidx.recyclerview.widget.DiffUtil
import com.suatzengin.iloveanimals.ui.advertisement.adapter.model.AdRecyclerItem
import com.suatzengin.iloveanimals.ui.advertisement.adapter.model.RecyclerItem

class AdvertisementDiffUtil : DiffUtil.ItemCallback<RecyclerItem>() {
    override fun areItemsTheSame(oldItem: RecyclerItem, newItem: RecyclerItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: RecyclerItem, newItem: RecyclerItem): Boolean {
        return (oldItem as AdRecyclerItem) == (newItem as AdRecyclerItem)
    }
}