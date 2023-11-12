package com.suatzengin.iloveanimals.ui.search.adapter

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.suatzengin.iloveanimals.databinding.ItemAdvertisementBinding
import com.suatzengin.iloveanimals.domain.model.advertisement.Advertisement

class SearchViewHolder(
    private val binding: ItemAdvertisementBinding,
    private val onItemClick: (Advertisement) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(advertisement: Advertisement) {
        binding.apply {
            tvAdTitle.text = advertisement.title
            tvAdDescription.text = advertisement.description
            tvAdAddress.text = advertisement.location.address

            ivMore.isVisible = advertisement.isImageSizeBiggerThan2

            if(advertisement.images.isNotEmpty()) ivAdImage1.load(advertisement.images[0])
            if(advertisement.isImageSizeBiggerThan1) ivAdImage2.load(advertisement.images[1])

            ivAdImage1.isVisible = advertisement.images.isNotEmpty()
            ivAdImage2.isVisible = advertisement.images.isNotEmpty()

            root.setOnClickListener {
                onItemClick(advertisement)
            }
        }
    }
}
