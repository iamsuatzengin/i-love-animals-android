package com.suatzengin.iloveanimals.ui.advertisementdetail

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.suatzengin.iloveanimals.R
import com.suatzengin.iloveanimals.core.viewbinding.viewBinding
import com.suatzengin.iloveanimals.data.model.advertisement.comment.AdCommentApiModel
import com.suatzengin.iloveanimals.databinding.FragmentAdvertisementDetailBinding
import com.suatzengin.iloveanimals.domain.model.advertisement.AdvertisementCategory
import com.suatzengin.iloveanimals.ui.advertisementdetail.adapter.ImagesPagerAdapter
import com.suatzengin.iloveanimals.ui.advertisementdetail.commentbottomsheet.AdDetailCommentBottomSheet
import com.suatzengin.iloveanimals.util.extension.showToast
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
        progressBar.isVisible = uiState.isLoading
        clContainer.isVisible = uiState.isLoading.not()

        btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        btnComments.setOnClickListener {
            showCommentsBottomSheet(uiState.comments)
        }

        uiState.advertisement?.let { advertisement ->
            setImagesViewPager(imageUrls = advertisement.images)
            tvTitle.text = advertisement.title
            tvCategory.text = AdvertisementCategory.getTitle(advertisement.category)
            tvDescription.text = advertisement.description
            tvAddress.text = advertisement.location.address

            tvStatus.setStatus(advertisement.isCompleted)
            buttonClicks(advertisement.isCompleted)

            tvCommentCount.text = uiState.comments.size.toString()
        }
    }

    private fun setImagesViewPager(imageUrls: List<String>) {
        binding.apply {
            viewPagerImages.adapter = ImagesPagerAdapter(imageUrls)

            TabLayoutMediator(tabLayout, viewPagerImages) { _, _ ->}.attach()
        }
    }

    private fun showCommentsBottomSheet(comments: List<AdCommentApiModel>) {
        val bottomSheet = AdDetailCommentBottomSheet()

        val array = comments.toTypedArray()

        val bundle = Bundle().apply {
            putParcelableArray(
                COMMENTS_BUNDLE_KEY, array
            )
        }

        childFragmentManager.setFragmentResult(COMMENTS_REQUEST_KEY, bundle)

        bottomSheet.show(childFragmentManager, "BOTTOM_SHEET_TAG")
    }

    private fun TextView.setStatus(isCompleted: Boolean) {
        if(isCompleted) {
            backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.color_success, null))
            text = resources.getString(R.string.text_advertisement_completed)
        } else {
            backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.color_waiting, null))
            text = resources.getString(R.string.text_advertisement_not_completed)
        }
    }

    private fun buttonClicks(isCompleted: Boolean) = with(binding){
        if(isCompleted) {
            btnHelp.isEnabled = false
            btnFindVet.isEnabled = false
            btnHelp.alpha = ALPHA_VISIBILITY
            btnFindVet.alpha = ALPHA_VISIBILITY
            return@with
        }

        btnHelp.setOnClickListener {
            showToast("Yardım et")
        }

        btnFindVet.setOnClickListener {
            findNavController().navigate(R.id.to_veterinaryClinicFragment)
        }
    }

    companion object {
        const val COMMENTS_REQUEST_KEY = "commentsRequestKey"
        const val COMMENTS_BUNDLE_KEY = "commentsBundleKey"

        const val ALPHA_VISIBILITY = 0.5f
    }
}
