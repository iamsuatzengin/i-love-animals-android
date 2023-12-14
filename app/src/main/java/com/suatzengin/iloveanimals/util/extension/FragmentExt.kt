package com.suatzengin.iloveanimals.util.extension

import android.widget.Toast
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

fun Fragment.showToast(text: String) {
    Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
}
