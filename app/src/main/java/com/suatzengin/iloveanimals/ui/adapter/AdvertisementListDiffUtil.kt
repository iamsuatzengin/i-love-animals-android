package com.suatzengin.iloveanimals.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.suatzengin.iloveanimals.domain.model.advertisement.Advertisement

class AdvertisementListDiffUtil : DiffUtil.ItemCallback<Advertisement>() {
    override fun areItemsTheSame(oldItem: Advertisement, newItem: Advertisement): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Advertisement, newItem: Advertisement): Boolean {
        return oldItem == newItem
    }
}
