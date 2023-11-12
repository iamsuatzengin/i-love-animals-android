package com.suatzengin.iloveanimals.core.ui

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.suatzengin.iloveanimals.util.extension.dpAsPixels

class MarginItemDecoration(
    private val top: Int = 8,
    private val bottom: Int = 8,
    private val left: Int = 16,
    private val right: Int = 16,
): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val marginTop = view.dpAsPixels(top)
        val marginBottom = view.dpAsPixels(bottom)
        val marginLeft = view.dpAsPixels(left)
        val marginRight = view.dpAsPixels(right)

        outRect.apply {
            top = marginTop
            bottom = marginBottom
            left = marginLeft
            right = marginRight
        }

    }
}