package com.suatzengin.iloveanimals.ui.advertisement.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.suatzengin.iloveanimals.databinding.ItemTopBinding
import com.suatzengin.iloveanimals.ui.advertisement.adapter.model.TopRecyclerItem

class TopViewHolder(
    private val binding: ItemTopBinding
) : ViewHolder(binding.root) {
    fun bind(
        item: TopRecyclerItem,
        setOnActionDone: ((String) -> Unit)? = null,
        filterButtonClickListener: (() -> Unit)? = null,
        notificationButtonClickListener: (() -> Unit)? = null,
    ) {
        binding.apply {

            etSearch.setOnActionDoneListener {
                println("tıklandı action done")
                println("ettext: ${etSearch.editText?.text}")
                setOnActionDone?.invoke(etSearch.editText?.text.toString())
            }

            etSearch.setOnEndIconClick {
                filterButtonClickListener?.invoke()
            }

            btnNotification.setOnClickListener {
                notificationButtonClickListener?.invoke()
            }
        }


    }
}