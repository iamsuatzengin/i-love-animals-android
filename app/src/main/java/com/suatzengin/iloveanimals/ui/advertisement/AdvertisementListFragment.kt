package com.suatzengin.iloveanimals.ui.advertisement

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.model.LatLng
import com.suatzengin.iloveanimals.R
import com.suatzengin.iloveanimals.core.ui.LoadingDialog
import com.suatzengin.iloveanimals.core.ui.MarginItemDecoration
import com.suatzengin.iloveanimals.core.viewbinding.viewBinding
import com.suatzengin.iloveanimals.databinding.FragmentAdvertisementListBinding
import com.suatzengin.iloveanimals.domain.model.advertisement.AdvertisementCategory
import com.suatzengin.iloveanimals.ui.advertisement.adapter.AdvertisementAdapter
import com.suatzengin.iloveanimals.ui.advertisement.callback.AdvertisementCallback
import com.suatzengin.iloveanimals.ui.advertisement.callback.CategoryCallback
import com.suatzengin.iloveanimals.ui.advertisement.callback.TopViewCallback
import com.suatzengin.iloveanimals.ui.map.LocationToAddressConverter
import com.suatzengin.iloveanimals.util.LocationManagerUtil
import com.suatzengin.iloveanimals.util.extension.getCurrentLocation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AdvertisementListFragment : Fragment(R.layout.fragment_advertisement_list) {
    private val binding by viewBinding(FragmentAdvertisementListBinding::bind)
    private val viewModel by viewModels<AdvertisementViewModel>()

    private var adapter: AdvertisementAdapter? = null

    private val addressConverter by lazy {
        LocationToAddressConverter(
            requireContext(),
            viewLifecycleOwner.lifecycleScope
        )
    }

    @Inject
    lateinit var locationManagerUtil: LocationManagerUtil

    private val loadingDialog: LoadingDialog by lazy {
        LoadingDialog(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        collectData()
        initView()
    }

    private fun initView() = with(binding) {
        layoutSwipeToRefresh.setOnRefreshListener {

            viewModel.getAdvertisementsAllOrOnlyNearby(isRefreshing = true)
        }
    }

    override fun onStart() {
        super.onStart()

        checkLocation()
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.rvAdvertisement
        val layoutManager = LinearLayoutManager(requireContext())

        adapter = AdvertisementAdapter(
            topViewCallback = topViewCallback,
            advertisementCallback = advertisementCallback,
            categoryCallback = categoryCallback
        )

        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = null
        recyclerView.addItemDecoration(MarginItemDecoration())
        recyclerView.adapter = adapter
    }

    private fun collectData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.uiState.collect { state ->
                    if (state.advertisementList.isEmpty()) {
                        Log.i("network - fragment", "Liste boş")
                    }

                    adapter?.submitList(state.recyclerItems)

                    binding.layoutSwipeToRefresh.isRefreshing = state.isRefreshing

                    loadingDialog.showLoading(state.isLoading)
                }

            }
        }
    }

    private val locationPermission = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false)
                    || permissions.getOrDefault(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                false
            ) -> {
                getCurrentLocation()
            }

            else -> {
                // No location access granted.
            }
        }
    }

    private fun checkLocation() {
        when {
            locationManagerUtil.isGpsEnabled() && isPermissionsGranted() -> {
                getCurrentLocation()
            }

            locationManagerUtil.isGpsEnabled() && isPermissionsGranted().not() -> {
                locationPermission.launch(LOCATION_PERMISSION)
            }

            locationManagerUtil.isGpsEnabled().not() && isPermissionsGranted() -> {
                locationManagerUtil.enableLocation(
                    onError = { resolution ->
                        val intentSenderRequest = IntentSenderRequest.Builder(resolution).build()

                        activityResult.launch(intentSenderRequest)
                    }
                )
            }

            else -> {
                viewModel.getAdvertisementsAllOrOnlyNearby()
            }
        }
    }

    private fun getCurrentLocation() {
        lifecycleScope.launch {
            runCatching {
                viewModel.updateIsLoading(true)
                val currentLocation = requireContext().getCurrentLocation()

                val latLng =
                    LatLng(currentLocation?.latitude ?: 0.0, currentLocation?.longitude ?: 0.0)

                addressConverter.getFromLocation(latLng) { location ->
                    viewModel.updatePostalCode(postalCode = location.postalCode)
                }
                viewModel.updateIsLoading(false)
            }.onFailure {
                Log.w("Location", "exception: $it")
            }
        }
    }

    private val activityResult = registerForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { activityResult ->
        if (activityResult.resultCode == Activity.RESULT_OK) {
            getCurrentLocation()
        }
    }

    private fun isPermissionsGranted() = !(ActivityCompat.checkSelfPermission(
        requireContext(),
        Manifest.permission.ACCESS_FINE_LOCATION
    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
        requireContext(),
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) != PackageManager.PERMISSION_GRANTED)

    private val topViewCallback = object : TopViewCallback {
        override fun onActionDoneClick(text: String) {
            if (text.isBlank()) return

            val action = AdvertisementListFragmentDirections.toSearchFragment(text)
            findNavController().navigate(action)
        }

        override fun onFilterButtonClick() {
            Toast.makeText(requireContext(), "Filter button tıklandı", Toast.LENGTH_SHORT).show()
        }

        override fun onNotificationButtonClick() {
            Toast.makeText(requireContext(), "Bildirim button tıklandı", Toast.LENGTH_SHORT).show()
        }
    }

    private val advertisementCallback = object : AdvertisementCallback {
        override fun onAdvertisementClick(id: String) {
            val action =
                AdvertisementListFragmentDirections.toAdvertisementDetailFragment(advertisementId = id)

            findNavController().navigate(action)
        }
    }

    private val categoryCallback = object : CategoryCallback {
        override fun onCategoryClick(category: AdvertisementCategory) {
            viewModel.updateSelectedCategory(category)

            when (category) {
                AdvertisementCategory.ALL -> viewModel.getAdvertisementsAllOrOnlyNearby()
                else -> viewModel.getAdvertisementsByCategory(category)
            }
        }
    }

    companion object {
        val LOCATION_PERMISSION = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }
}
