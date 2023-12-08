package com.suatzengin.iloveanimals.ui.map

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import com.google.android.gms.maps.model.LatLng
import com.suatzengin.iloveanimals.util.extension.ZERO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class LocationToAddressConverter(
    private val context: Context,
    private val lifecycleScope: CoroutineScope
) {
    private val _state = MutableStateFlow(Location())
    val state = _state.asStateFlow()

    fun getFromLocation(location: LatLng) {
        lifecycleScope.launch {
            getFromLocation(location.longitude, location.latitude).collect { address ->
                _state.emit(
                    Location(
                        latitude = location.latitude,
                        longitude = location.longitude,
                        address = address
                    )
                )
            }
        }
    }

    private fun getFromLocation(longitude: Double, latitude: Double) = callbackFlow {
        val geocoder = Geocoder(context)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            geocoder.getFromLocation(
                latitude, longitude, ONLY_ONE_RESULT
            ) { addresses ->
                trySend(getAddress(addresses[ZERO]))
            }

        } else {
            geocoder.getFromLocation(
                latitude, longitude, ONLY_ONE_RESULT
            )?.let {
                trySend(getAddress(it[ZERO]))
            }
        }

        awaitClose()
    }.flowOn(Dispatchers.IO)


    private fun getAddress(address: Address): String = address.getAddressLine(ZERO)


    data class Location(
        val latitude: Double = 0.0,
        val longitude: Double = 0.0,
        val address: String? = null,
    )

    companion object {
        const val ONLY_ONE_RESULT = 1
    }
}
