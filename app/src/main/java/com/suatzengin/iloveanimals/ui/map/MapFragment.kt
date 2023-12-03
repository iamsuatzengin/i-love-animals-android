package com.suatzengin.iloveanimals.ui.map

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.ktx.addMarker
import com.google.maps.android.ktx.awaitMap
import com.google.maps.android.ktx.infoWindowClickEvents
import com.google.maps.android.ktx.mapLongClickEvents
import com.suatzengin.iloveanimals.R
import com.suatzengin.iloveanimals.core.viewbinding.viewBinding
import com.suatzengin.iloveanimals.databinding.FragmentMapBinding
import com.suatzengin.iloveanimals.util.extension.dpAsPixels
import kotlinx.coroutines.launch

class MapFragment : Fragment(R.layout.fragment_map) {
    private val binding by viewBinding(FragmentMapBinding::bind)

    private val currentLocation = LatLng(38.482763, 27.704220)

    private val myAddress by lazy { LocationToAddressConverter(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initGoogleMaps()
    }

    private fun initGoogleMaps() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                val googleMap = mapFragment.awaitMap()

                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 20F))

                googleMap.setPaddingAsDp(
                    view = binding.root,
                    left = 0,
                    top = 24,
                    right = 16,
                    bottom = 0
                )

                isMyLocationEnabled(googleMap)

                launch {
                    googleMap.mapLongClickEvents().collect { latLng ->
                        googleMap.addMarker {
                            position(latLng)
                            title("Bu adresi seç.")
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20f))
                        }

                        Log.i("Location", "Longitude: ${latLng.longitude}")
                        Log.i("Location", "latitude: ${latLng.latitude}")
                    }
                }

                launch {
                    googleMap.infoWindowClickEvents().collect {
                        myAddress.getFromLocation(it.position.longitude, it.position.latitude)
                    }
                }
            }
        }
    }


    private fun isMyLocationEnabled(googleMap: GoogleMap) {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) return

        googleMap.isMyLocationEnabled = true
    }

    private fun GoogleMap.setPaddingAsDp(
        view: View,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ) {
        setPadding(
            view.dpAsPixels(left),
            view.dpAsPixels(top),
            view.dpAsPixels(right),
            view.dpAsPixels(bottom),
        )
    }
}