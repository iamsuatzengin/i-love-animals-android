package com.suatzengin.iloveanimals.ui.camera.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.suatzengin.iloveanimals.databinding.ItemCameraImageBinding

class CameraAdapter(
    private val onDeleteClick: (Uri) -> Unit
) : ListAdapter<Uri, CameraViewHolder>(ImageDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CameraViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding = ItemCameraImageBinding.inflate(layoutInflater, parent, false)

        return CameraViewHolder(binding = binding, onDeleteClick = onDeleteClick)
    }

    override fun onBindViewHolder(holder: CameraViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item)
    }
}

class CameraViewHolder(
    private val binding: ItemCameraImageBinding,
    private val onDeleteClick: (Uri) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Uri) {
        binding.apply {
            ivAdImage.setImageURI(item)

            btnDelete.setOnClickListener {
                onDeleteClick(item)
            }
        }
    }
}

class ImageDiffUtil : DiffUtil.ItemCallback<Uri>() {
    override fun areItemsTheSame(oldItem: Uri, newItem: Uri): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: Uri, newItem: Uri): Boolean = oldItem == newItem
}
