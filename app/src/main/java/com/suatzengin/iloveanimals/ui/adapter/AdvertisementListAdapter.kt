package com.suatzengin.iloveanimals.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.suatzengin.iloveanimals.databinding.ItemAdvertisementBinding
import com.suatzengin.iloveanimals.domain.model.advertisement.Advertisement

class AdvertisementListAdapter(
    private val onItemClick: (Advertisement) -> Unit
) : ListAdapter<Advertisement, AdvertisementListViewHolder>(AdvertisementListDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdvertisementListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding = ItemAdvertisementBinding.inflate(layoutInflater, parent, false)

        return AdvertisementListViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: AdvertisementListViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(advertisement = item)
    }
}
