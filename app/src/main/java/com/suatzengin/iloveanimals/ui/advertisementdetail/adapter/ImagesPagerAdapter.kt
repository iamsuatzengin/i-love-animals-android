package com.suatzengin.iloveanimals.ui.advertisementdetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.suatzengin.iloveanimals.databinding.ItemViewPagerImageBinding

class ImagesPagerAdapter(
    private val imageUrls: List<String>
): RecyclerView.Adapter<ImagesPagerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesPagerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding = ItemViewPagerImageBinding.inflate(layoutInflater, parent, false)

        return ImagesPagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImagesPagerViewHolder, position: Int) {
        val image = imageUrls[position]

        holder.bind(imageUrl = image)
    }

    override fun getItemCount(): Int = imageUrls.size
}

class ImagesPagerViewHolder(
    private val binding: ItemViewPagerImageBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(imageUrl: String) {
        binding.ivAdImage.load(imageUrl) {
            crossfade(true)
        }
    }
}
