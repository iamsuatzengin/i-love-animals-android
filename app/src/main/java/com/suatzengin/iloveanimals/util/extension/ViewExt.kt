package com.suatzengin.iloveanimals.util.extension

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View.hideKeyboard() {
    val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}

fun View.dpAsPixels(size: Int): Int {
    val scale = resources.displayMetrics.density
    return (size * scale + 0.5f).toInt()
}