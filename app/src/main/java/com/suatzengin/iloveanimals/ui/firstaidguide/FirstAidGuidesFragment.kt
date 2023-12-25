package com.suatzengin.iloveanimals.ui.firstaidguide

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.suatzengin.iloveanimals.ui.firstaidguide.model.FirstAidUiModel

class FirstAidGuidesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )

            setContent {
                MaterialTheme {
                    FirstAidGuidesScreen(
                        onNavigateBackClick = {
                            findNavController().navigateUp()
                        },
                        onItemClick = ::navigateToDetail
                    )
                }
            }
        }
    }

    private fun navigateToDetail(firstAidUiModel: FirstAidUiModel) {
        val action = FirstAidGuidesFragmentDirections.toFirstAidGuideDetailFragment(firstAidUiModel)
        findNavController().navigate(action)
    }
}
