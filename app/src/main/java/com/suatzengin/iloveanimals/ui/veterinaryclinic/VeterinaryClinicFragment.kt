package com.suatzengin.iloveanimals.ui.veterinaryclinic

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
            VeterinaryClinicScreen(viewModel)
        }
    }
}
