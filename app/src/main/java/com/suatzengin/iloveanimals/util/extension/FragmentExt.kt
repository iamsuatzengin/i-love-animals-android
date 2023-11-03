package com.suatzengin.iloveanimals.util.extension

import androidx.fragment.app.Fragment
import com.suatzengin.iloveanimals.core.ui.snackbar.Snackbom
import com.suatzengin.iloveanimals.core.ui.snackbar.SnackbomType

fun Fragment.showSnackbar(type: SnackbomType, text: String) {
    Snackbom.make(
        view = requireView(),
        text = text,
        type = type
    ).show()
}