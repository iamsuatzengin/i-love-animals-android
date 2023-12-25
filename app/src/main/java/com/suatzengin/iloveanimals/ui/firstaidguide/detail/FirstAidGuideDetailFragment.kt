package com.suatzengin.iloveanimals.ui.firstaidguide.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs

class FirstAidGuideDetailFragment : Fragment() {
    private val args by navArgs<FirstAidGuideDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                FirstAidGuideDetailScreen(firstAidUiModel = args.firstAidUiModel)
            }
        }
    }
}
