package com.suatzengin.iloveanimals.ui.map

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import com.suatzengin.iloveanimals.util.extension.ZERO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class LocationToAddressConverter(
    private val context: Context,
) : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    suspend fun getFromLocation(longitude: Double, latitude: Double) = withContext(coroutineContext) {
        val geocoder = Geocoder(context)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            geocoder.getFromLocation(
                latitude, longitude, ONLY_ONE_RESULT
            ) { addresses ->
                getAddress(addresses[ZERO])
            }

            return@withContext
        }

        geocoder.getFromLocation(
            latitude, longitude, ONLY_ONE_RESULT
        )?.let {
            getAddress(it[ZERO])
        }
    }

    private fun getAddress(address: Address): String = address.getAddressLine(ZERO)

    companion object {
        const val ONLY_ONE_RESULT = 1
    }
}
