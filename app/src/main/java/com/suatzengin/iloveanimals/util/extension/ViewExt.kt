package com.suatzengin.iloveanimals.util.extension

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun View.hideKeyboard() {
    val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}

fun View.dpAsPixels(size: Int): Int {
    val scale = resources.displayMetrics.density
    return (size * scale + 0.5f).toInt()
}

fun View.delayOnLifecycle(
    delayMillis: Long,
    action: () -> Unit
) {
    findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
        findViewTreeLifecycleOwner()?.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            delay(delayMillis)
            action()
        }
    }
}
