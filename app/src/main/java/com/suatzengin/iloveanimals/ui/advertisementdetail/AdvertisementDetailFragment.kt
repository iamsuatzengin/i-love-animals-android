package com.suatzengin.iloveanimals.ui.advertisementdetail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.tabs.TabLayoutMediator
import com.suatzengin.iloveanimals.R
import com.suatzengin.iloveanimals.core.viewbinding.viewBinding
import com.suatzengin.iloveanimals.databinding.FragmentAdvertisementDetailBinding
import com.suatzengin.iloveanimals.domain.model.advertisement.AdvertisementCategory
import com.suatzengin.iloveanimals.ui.advertisementdetail.adapter.ImagesPagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AdvertisementDetailFragment : Fragment(R.layout.fragment_advertisement_detail) {
    private val binding by viewBinding(FragmentAdvertisementDetailBinding::bind)

    private val viewModel by viewModels<AdDetailViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        collectData()
    }

    private fun collectData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest { uiState ->
                    Log.i("Data", "Veri geldi: ${uiState.advertisement}")

                    initView(uiState)
                }
            }
        }
    }

    private fun initView(uiState: AdDetailUiState) = with(binding) {

        uiState.advertisement?.let { advertisement ->
            setImagesViewPager(imageUrls = advertisement.images)
            tvTitle.text = advertisement.title
            tvCategory.text = AdvertisementCategory.getTitle(advertisement.category)
            tvDescription.text = advertisement.description
            tvAddress.text = advertisement.location.address
        }

    }

    private fun setImagesViewPager(imageUrls: List<String>) {
        binding.apply {
            viewPagerImages.adapter = ImagesPagerAdapter(imageUrls)

            TabLayoutMediator(tabLayout, viewPagerImages) { _, _ ->}.attach()
        }
    }
}