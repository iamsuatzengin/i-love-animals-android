package com.suatzengin.iloveanimals.ui.map

import androidx.lifecycle.ViewModel

class MapViewModel : ViewModel() {

    var markerExists: Boolean = false
        set(value) {
            field = value
        }
}
