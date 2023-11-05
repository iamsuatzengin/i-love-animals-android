package com.suatzengin.iloveanimals.ui.advertisement.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.suatzengin.iloveanimals.databinding.ItemAdvertisementBinding
import com.suatzengin.iloveanimals.domain.model.advertisement.Advertisement

class AdvertisementAdapter : ListAdapter<Advertisement, AdvertisementViewHolder>(AdvertisementDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdvertisementViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = ItemAdvertisementBinding.inflate(inflater, parent, false)

        return AdvertisementViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdvertisementViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(advertisement = item)
    }

}

class AdvertisementViewHolder(
    private val binding: ItemAdvertisementBinding
) : ViewHolder(binding.root) {
    fun bind(advertisement: Advertisement) {
        binding.apply {
            tvAdTitle.text = advertisement.title
            tvAdDescription.text = advertisement.description
            tvAdAddress.text = advertisement.location.address

            ivMore.isVisible = advertisement.isImageSizeBiggerThan2

            if(advertisement.images.isNotEmpty()) ivAdImage1.load(advertisement.images[0])
            if(advertisement.images.size >= 2) ivAdImage2.load(advertisement.images[1])

            ivAdImage1.isVisible = advertisement.images.isNotEmpty()
            ivAdImage2.isVisible = advertisement.images.isNotEmpty()

        }
    }

}

class AdvertisementDiffUtil: DiffUtil.ItemCallback<Advertisement>() {
    override fun areItemsTheSame(oldItem: Advertisement, newItem: Advertisement): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Advertisement, newItem: Advertisement): Boolean {
        return oldItem == newItem
    }

}