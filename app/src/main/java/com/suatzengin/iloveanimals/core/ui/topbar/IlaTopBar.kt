package com.suatzengin.iloveanimals.core.ui.topbar

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import com.suatzengin.iloveanimals.R
import com.suatzengin.iloveanimals.databinding.ViewIlaTopBarBinding

class IlaTopBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: ViewIlaTopBarBinding = ViewIlaTopBarBinding.inflate(
        LayoutInflater.from(context),
        this
    )

    init {
        initView(context, attrs)
    }

    private fun initView(context: Context, attrs: AttributeSet?) {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.IlaTopBar)

        setTitle(attributes.getString(R.styleable.IlaTopBar_title).orEmpty())

        setTitleCentered(attributes.getBoolean(R.styleable.IlaTopBar_isTitleCentered, false))

        setTitleColor(
            attributes.getColor(
                R.styleable.IlaTopBar_titleColor,
                Color.BLACK
            )
        )

        setNavigationIcon(attributes.getDrawable(R.styleable.IlaTopBar_leftIcon))

        setActionButtonLeft(attributes.getDrawable(R.styleable.IlaTopBar_actionLeftIcon))

        setActionButtonRight(attributes.getDrawable(R.styleable.IlaTopBar_actionRightIcon))

        attributes.recycle()
    }

    fun setTitle(title: String?) {
        if (title.isNullOrEmpty()) {
            binding.tvTitle.visibility = View.INVISIBLE
            return
        }

        binding.tvTitle.text = title
    }

    fun setTitleCentered(isCentered: Boolean) {
        if (isCentered) {
            binding.tvTitle.gravity = Gravity.CENTER_HORIZONTAL
            return
        }
    }

    fun setTitleColor(colorId: Int) {
        binding.tvTitle.setTextColor(colorId)
    }

    fun setNavigationIcon(drawable: Drawable?) {
        if(drawable == null) {
            binding.leftIcon.visibility = View.GONE
            return
        }
        binding.leftIcon.setImageDrawable(drawable)
    }

    fun setActionButtonLeft(drawable: Drawable?) {
        binding.iconActionLeft.setImageDrawable(drawable)
    }

    fun setActionButtonRight(drawable: Drawable?) {
        binding.iconActionRight.setImageDrawable(drawable)
    }

    fun setOnLeftIconClick(onClick: () -> Unit) {
        binding.leftIcon.setOnClickListener {
            onClick()
        }
    }

    fun setOnActionLeftClick(onClick: () -> Unit) {
        binding.iconActionLeft.setOnClickListener {
            onClick()
        }
    }

    fun setOnActionRightClick(onClick: () -> Unit) {
        binding.iconActionRight.setOnClickListener {
            onClick()
        }
    }

}

@ColorInt
fun View.getColor(colorId: Int): Int = resources.getColor(colorId, null)
