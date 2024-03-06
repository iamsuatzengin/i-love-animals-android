package com.suatzengin.iloveanimals.ui.advertisementdetail.adapter.pager

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.suatzengin.iloveanimals.databinding.ItemViewPagerImageBinding

class ImagesPagerViewHolder(
    private val binding: ItemViewPagerImageBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(imageUrl: String) {
        binding.ivAdImage.load(imageUrl) {
            crossfade(true)
        }
    }
}
