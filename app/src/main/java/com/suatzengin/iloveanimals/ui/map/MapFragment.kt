package com.suatzengin.iloveanimals.ui.map

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.ktx.addMarker
import com.google.maps.android.ktx.awaitMap
import com.suatzengin.iloveanimals.R
import com.suatzengin.iloveanimals.core.viewbinding.viewBinding
import com.suatzengin.iloveanimals.databinding.FragmentMapBinding
import com.suatzengin.iloveanimals.util.extension.dpAsPixels
import com.suatzengin.iloveanimals.util.extension.getCurrentLocation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MapFragment : Fragment(R.layout.fragment_map) {
    private val binding by viewBinding(FragmentMapBinding::bind)

    private val viewModel by viewModels<MapViewModel>()

    private val args by navArgs<MapFragmentArgs>()

    private val myAddress by lazy { LocationToAddressConverter(requireContext(), lifecycleScope) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initGoogleMaps()
    }

    private fun initGoogleMaps() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                val googleMap = mapFragment.awaitMap()

                val lastLocation = requireContext().getCurrentLocation()

                val latLng = LatLng(
                    lastLocation?.latitude ?: 0.0,
                    lastLocation?.longitude ?: 0.0
                )

                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, ZOOM))

                googleMap.setPaddingAsDp(view = binding.root)

                isMyLocationEnabled(googleMap)

                myAddress.getFromLocation(latLng) { location ->
                    updateLocationInformation(location)
                }

                googleMap.setOnMapLongClickListener {
                    if (viewModel.markerExists) {
                        googleMap.clear()
                        googleMap.addMarker {
                            position(it)
                            title("Bu adresi seç.")
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(it, ZOOM))
                        }
                    } else {
                        googleMap.addMarker {
                            position(it)
                            title("Bu adresi seç.")
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(it, ZOOM))
                        }.also { viewModel.markerExists = true }
                    }

                    myAddress.getFromLocation(LatLng(it.latitude, it.longitude)) { location ->
                        updateLocationInformation(location)
                    }
                }
            }
        }
    }

    private fun updateLocationInformation(
        location: LocationToAddressConverter.Location,
    ) {
        location.address?.let { address ->
            binding.textField.editText?.setText(address)

            binding.btnSave.setOnClickListener {
                val state = args.advertisement.copy(
                    longitude = location.latitude.toString(),
                    latitude = location.longitude.toString(),
                    address = binding.textField.editText?.text.toString(),
                    postalCode = location.postalCode.orEmpty()
                )

                val action = MapFragmentDirections.toConfirmAdvertisementFragment(state)
                findNavController().navigate(action)
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
        left: Int = 0,
        top: Int = 24,
        right: Int = 16,
        bottom: Int = 0
    ) {
        setPadding(
            view.dpAsPixels(left),
            view.dpAsPixels(top),
            view.dpAsPixels(right),
            view.dpAsPixels(bottom),
        )
    }

    companion object {
        const val ZOOM = 20f
    }
}
