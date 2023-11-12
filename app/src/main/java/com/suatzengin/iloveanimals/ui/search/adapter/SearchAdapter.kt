package com.suatzengin.iloveanimals.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.suatzengin.iloveanimals.databinding.ItemAdvertisementBinding
import com.suatzengin.iloveanimals.domain.model.advertisement.Advertisement

class SearchAdapter(
    private val onItemClick: (Advertisement) -> Unit
) : ListAdapter<Advertisement, SearchViewHolder>(SearchDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding = ItemAdvertisementBinding.inflate(layoutInflater, parent, false)

        return SearchViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(advertisement = item)
    }
}
