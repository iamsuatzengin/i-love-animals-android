package com.suatzengin.iloveanimals.util.extension

import android.os.Build
import android.os.Bundle

@Suppress("DEPRECATION", "UNCHECKED_CAST")
inline fun <reified T> Bundle.getParcelableList(key: String): List<T> {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelableArray(key, T::class.java)
    } else {
        getParcelableArray(key) as? Array<T>
    }?.toList() ?: emptyList()
}
