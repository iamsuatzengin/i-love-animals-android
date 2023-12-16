package com.suatzengin.iloveanimals.ui.veterinaryclinic

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.suatzengin.iloveanimals.R
import com.suatzengin.iloveanimals.core.viewbinding.viewBinding
import com.suatzengin.iloveanimals.databinding.FragmentVeterinaryClinicBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VeterinaryClinicFragment : Fragment(R.layout.fragment_veterinary_clinic) {
    private val binding by viewBinding(FragmentVeterinaryClinicBinding::bind)

    private val viewModel by viewModels<VeterinaryClinicViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.composeView.setContent {
            VeterinaryClinicScreen(
                viewModel = viewModel,
                onNavigateBackClick = {
                    findNavController().navigateUp()
                },
                onDirectionButtonClick = ::navigateToDirection,
                onCallButtonClick = ::navigateToActionDial
            )
        }
    }

    private fun navigateToDirection(latitude: String, longitude: String) {
        val intent = Uri.parse("google.navigation:q=$latitude,$longitude").let {  uri ->
            Intent(Intent.ACTION_VIEW, uri)
        }

        startActivity(intent)
    }

    private fun navigateToActionDial(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))

        startActivity(intent)
    }
}
