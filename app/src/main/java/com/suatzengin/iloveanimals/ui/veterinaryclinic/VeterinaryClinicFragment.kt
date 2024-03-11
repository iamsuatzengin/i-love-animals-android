package com.suatzengin.iloveanimals.ui.veterinaryclinic

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VeterinaryClinicFragment : Fragment() {
    private val viewModel by viewModels<VeterinaryClinicViewModel>()

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
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                MaterialTheme {
                    VeterinaryClinicScreen(
                        uiState = uiState,
                        onNavigateBackClick = {
                            findNavController().navigateUp()
                        },
                        onDirectionButtonClick = ::navigateToDirection,
                        onCallButtonClick = ::navigateToActionDial
                    )
                }
            }
        }
    }

    private fun navigateToDirection(latitude: String, longitude: String) {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("google.navigation:q=$latitude,$longitude")
        )

        startActivity(intent)
    }

    private fun navigateToActionDial(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))

        startActivity(intent)
    }
}
