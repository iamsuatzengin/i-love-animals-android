package com.suatzengin.iloveanimals.ui.map

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
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
import com.google.maps.android.ktx.mapLongClickEvents
import com.suatzengin.iloveanimals.R
import com.suatzengin.iloveanimals.core.viewbinding.viewBinding
import com.suatzengin.iloveanimals.databinding.FragmentMapBinding
import com.suatzengin.iloveanimals.util.extension.dpAsPixels
import kotlinx.coroutines.launch

class MapFragment : Fragment(R.layout.fragment_map) {
    private val binding by viewBinding(FragmentMapBinding::bind)

    private val london = LatLng(51.403186, -0.126446)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initGoogleMaps()
    }

    private fun initGoogleMaps() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                val googleMap = mapFragment.awaitMap()

                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(london, 10F))
                googleMap.setPaddingAsDp(
                    view = binding.root,
                    left = 0,
                    top = 24,
                    right = 16,
                    bottom = 0
                )

                isMyLocationEnabled(googleMap)

                googleMap.mapLongClickEvents().collect { latlng ->
                    googleMap.addMarker {
                        position(latlng)
                        title("Konum")
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 10f))
                    }

                    Log.i("Location", "Longitude: ${latlng.longitude}")
                    Log.i("Location", "latitude: ${latlng.latitude}")
                }
            }
        }
    }

    private fun isMyLocationEnabled(googleMap: GoogleMap) {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) return

        googleMap.isMyLocationEnabled = true
        googleMap.setOnMyLocationClickListener {
            Log.i("Location", "Longitude: ${it.longitude}")
            Log.i("Location", "latitude: ${it.latitude}")
        }
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