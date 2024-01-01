package com.suatzengin.iloveanimals.ui.map

import android.content.Context
import android.location.Geocoder
import android.os.Build
import com.google.android.gms.maps.model.LatLng
import com.suatzengin.iloveanimals.util.extension.ZERO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class LocationToAddressConverter(
    private val context: Context,
    private val lifecycleScope: CoroutineScope
) {
    fun getFromLocation(
        location: LatLng,
        locationListener: (postalCode: Location) -> Unit
    ) {
        lifecycleScope.launch {
            getFromLocation(location.longitude, location.latitude).collect { address ->
                locationListener(
                    Location(
                        latitude = location.latitude,
                        longitude = location.longitude,
                        address = address.getAddressLine(ZERO),
                        postalCode = address.postalCode
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
                trySend(addresses[ZERO])
            }

        } else {
            geocoder.getFromLocation(
                latitude, longitude, ONLY_ONE_RESULT
            )?.let {
                trySend(it[ZERO])
            }
        }

        awaitClose()
    }.flowOn(Dispatchers.IO)

    data class Location(
        val latitude: Double = 0.0,
        val longitude: Double = 0.0,
        val address: String? = null,
        val postalCode: String? = null,
    )

    companion object {
        const val ONLY_ONE_RESULT = 1
    }
}
