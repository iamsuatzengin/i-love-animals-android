package com.suatzengin.iloveanimals.ui.firstaidguide

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.suatzengin.iloveanimals.R
import com.suatzengin.iloveanimals.core.viewbinding.viewBinding
import com.suatzengin.iloveanimals.databinding.FragmentFirstAidGuidesBinding
import com.suatzengin.iloveanimals.ui.firstaidguide.model.FirstAidUiModel

class FirstAidGuidesFragment : Fragment(R.layout.fragment_first_aid_guides) {
    private val binding by viewBinding(FragmentFirstAidGuidesBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.composeView.setContent {
            FirstAidGuidesScreen(
                onNavigateBackClick = {
                    findNavController().navigateUp()
                },
                onItemClick = ::navigateToDetail
            )
        }
    }

    private fun navigateToDetail(firstAidUiModel: FirstAidUiModel) {
        val action = FirstAidGuidesFragmentDirections.toFirstAidGuideDetailFragment(firstAidUiModel)
        findNavController().navigate(action)
    }
}
