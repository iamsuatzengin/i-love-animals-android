package com.suatzengin.iloveanimals.ui.createadvertisement.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.suatzengin.iloveanimals.databinding.ItemImageListBinding

class ImageAdapter : ListAdapter<Uri, ImageViewHolder>(ImageDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemImageListBinding.inflate(layoutInflater, parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item)
    }
}

class ImageViewHolder(
    private val binding: ItemImageListBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Uri) {
        binding.ivAdImage.load(item)
    }

}

class ImageDiffUtil : DiffUtil.ItemCallback<Uri>() {
    override fun areItemsTheSame(oldItem: Uri, newItem: Uri): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: Uri, newItem: Uri): Boolean = oldItem == newItem
}
