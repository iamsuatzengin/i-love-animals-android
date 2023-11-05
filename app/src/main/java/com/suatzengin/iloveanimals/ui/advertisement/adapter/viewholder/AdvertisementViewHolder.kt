package com.suatzengin.iloveanimals.ui.advertisement.adapter.viewholder

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.suatzengin.iloveanimals.databinding.ItemAdvertisementBinding
import com.suatzengin.iloveanimals.ui.advertisement.adapter.model.AdRecyclerItem

class AdvertisementViewHolder(
    private val binding: ItemAdvertisementBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(advertisement: AdRecyclerItem) {
        binding.apply {
            tvAdTitle.text = advertisement.title
            tvAdDescription.text = advertisement.description
            tvAdAddress.text = advertisement.address

            ivMore.isVisible = advertisement.isImageSizeBiggerThan2

            if(advertisement.images.isNotEmpty()) ivAdImage1.load(advertisement.images[0])
            if(advertisement.images.size >= 2) ivAdImage2.load(advertisement.images[1])

            ivAdImage1.isVisible = advertisement.images.isNotEmpty()
            ivAdImage2.isVisible = advertisement.images.isNotEmpty()

        }
    }
}