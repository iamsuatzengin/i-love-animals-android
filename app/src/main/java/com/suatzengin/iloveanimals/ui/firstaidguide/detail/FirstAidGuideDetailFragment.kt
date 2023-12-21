package com.suatzengin.iloveanimals.ui.firstaidguide.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.suatzengin.iloveanimals.R
import com.suatzengin.iloveanimals.core.viewbinding.viewBinding
import com.suatzengin.iloveanimals.databinding.FragmentFirstAidGuideDetailBinding
import com.suatzengin.iloveanimals.ui.firstaidguide.FirstAidGuideDetailScreen

class FirstAidGuideDetailFragment : Fragment(R.layout.fragment_first_aid_guide_detail) {
    private val binding by viewBinding(FragmentFirstAidGuideDetailBinding::bind)

    private val args by navArgs<FirstAidGuideDetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.composeView.setContent {
            FirstAidGuideDetailScreen(firstAidUiModel = args.firstAidUiModel)
        }
    }
}
