package com.suatzengin.iloveanimals.util

import android.app.PendingIntent
import android.content.Context
import android.location.LocationManager
import android.util.Log
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LocationManagerUtil @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val locationManager = context.getSystemService(
        Context.LOCATION_SERVICE
    ) as LocationManager

    fun isGpsEnabled() = runCatching {
        locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }.onFailure { exception ->
        Log.i("Location", "$exception")
    }.getOrDefault(false)

    fun enableLocation(onError: (PendingIntent) -> Unit) {
        val locationRequest = LocationRequest.Builder(INTERVAL_IN_MILLIS)
        val locationSettingsRequest = LocationSettingsRequest.Builder().addLocationRequest(
            locationRequest.build()
        )

        val task = LocationServices.getSettingsClient(context).checkLocationSettings(
            locationSettingsRequest.build()
        )

        task.addOnFailureListener { exception ->
            Log.i("Location - Error", "hata: $exception")

            val statusCode = (exception as ResolvableApiException).statusCode

            if (statusCode == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
                runCatching {
                    onError(exception.resolution)
                }.onFailure {
                    Log.i("Location - error start res.", "$it")
                }
            }
        }
    }

    companion object {
        const val INTERVAL_IN_MILLIS: Long = 3000
    }
}
