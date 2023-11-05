package com.suatzengin.iloveanimals.core.ui

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.FrameLayout
import com.suatzengin.iloveanimals.R
import com.suatzengin.iloveanimals.databinding.ViewEditTextBinding
import com.suatzengin.iloveanimals.util.extension.EMPTY_STRING
import com.suatzengin.iloveanimals.util.extension.hideKeyboard

class IlaEditText constructor(
    context: Context,
    private val attrs: AttributeSet?
) : FrameLayout(context, attrs), OnFocusChangeListener {

    private val binding = ViewEditTextBinding.inflate(
        LayoutInflater.from(context),
        this
    )

    init {
        initView(context)
    }

    var text: String = EMPTY_STRING
        set(value) {
            field = value
            binding.editText.setText(value)
        }

    var lines: Int = 1
        set(value) {
            field = value
            binding.editText.setLines(value)
        }

    var maxLines: Int = 1
        set(value) {
            field = value
            binding.editText.maxLines = value
        }

    var minLines: Int = 1
        set(value) {
            field = value
            binding.editText.minLines = value
        }

    var hint: String = ""
        set(value) {
            field = value
            binding.editText.hint = value
        }

    var editText: EditText? = null
        set(value) {
            field = value
        }

    private fun initView(context: Context) {
        val attrs = context.obtainStyledAttributes(attrs, R.styleable.IlaEditText)

        editText = binding.editText
        lines = attrs.getInt(R.styleable.IlaEditText_lines, 1)
        maxLines = attrs.getInt(R.styleable.IlaEditText_maxLines, 1)
        minLines = attrs.getInt(R.styleable.IlaEditText_minLines, 1)
        hint = attrs.getString(R.styleable.IlaEditText_hint).orEmpty()

        setStartIcon(icon = attrs.getDrawable(R.styleable.IlaEditText_startIcon))
        setEndIcon(icon = attrs.getDrawable(R.styleable.IlaEditText_endIcon))

        binding.editText.onFocusChangeListener = this

        attrs.recycle()
    }

    fun setStartIcon(icon: Drawable?) {
        binding.ivStartIcon.setImageDrawable(icon)
    }

    fun setEndIcon(icon: Drawable?) {
        binding.ivEndIcon.setImageDrawable(icon)
    }

    fun setOnEndIconClick(onClick: () -> Unit) {
        binding.ivEndIcon.setOnClickListener { onClick() }
    }

    fun setOnActionDoneListener(action: () -> Unit) {
        binding.editText.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEARCH) {
                action()
                return@setOnEditorActionListener true
            }
            false
        }
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if (!hasFocus) hideKeyboard()
    }
}
