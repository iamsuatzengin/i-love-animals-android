package com.suatzengin.iloveanimals.ui.advertisement.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.suatzengin.iloveanimals.databinding.ItemTopBinding
import com.suatzengin.iloveanimals.ui.advertisement.callback.TopViewCallback

class TopViewHolder(
    private val binding: ItemTopBinding,
    private val callback: TopViewCallback
) : ViewHolder(binding.root) {
    fun bind() {
        binding.apply {
            etSearch.setOnActionDoneListener {
                callback.onActionDoneClick(etSearch.editText?.text.toString())
            }

            etSearch.setOnEndIconClick {
                callback.onFilterButtonClick()
            }

            btnNotification.setOnClickListener {
                callback.onNotificationButtonClick()
            }
        }
    }
}
