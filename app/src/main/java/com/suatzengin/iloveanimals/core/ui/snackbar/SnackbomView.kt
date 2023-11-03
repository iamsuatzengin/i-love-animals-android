package com.suatzengin.iloveanimals.core.ui.snackbar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.ContentViewCallback
import com.suatzengin.iloveanimals.R
import com.suatzengin.iloveanimals.databinding.ViewSnackbomBinding

class SnackbomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ContentViewCallback {

    private val binding: ViewSnackbomBinding = ViewSnackbomBinding.inflate(
        LayoutInflater.from(context), this
    )

    fun setText(text: String) {
        binding.tvSnackbarMessage.text = text
    }

    fun setType(type: SnackbomType) {
        when (type) {
            SnackbomType.SUCCESS -> {
                setBackground(R.drawable.bg_snackbom_success)
                setIcon(R.drawable.ic_circle_done)
            }
            SnackbomType.ERROR -> {
                setBackground(R.drawable.bg_snackbom_error)
                setIcon(R.drawable.ic_circle_xmark)
            }
            SnackbomType.INFO -> {
                setBackground(R.drawable.bg_snackbom_info)
                setIcon(R.drawable.ic_circle_info)
            }
        }

        setMessageTextColor(R.color.white)
    }

    private fun setIcon(icon: Int) {
        binding.ivSnackbarIcon.setImageResource(icon)
    }

    private fun setBackground(@DrawableRes background: Int) {
        binding.root.background = ContextCompat.getDrawable(this.context, background)
    }

    private fun setMessageTextColor(@ColorRes color: Int) {
        binding.tvSnackbarMessage.setTextColor(ContextCompat.getColor(this.context, color))
    }

    override fun animateContentIn(delay: Int, duration: Int) {}

    override fun animateContentOut(delay: Int, duration: Int) {}
}
