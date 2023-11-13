package com.suatzengin.iloveanimals.ui.createadvertisement

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.suatzengin.iloveanimals.R
import com.suatzengin.iloveanimals.core.viewbinding.viewBinding
import com.suatzengin.iloveanimals.databinding.FragmentCreateAdvertisementBinding

class CreateAdvertisementFragment : Fragment(R.layout.fragment_create_advertisement) {
    private val binding by viewBinding(FragmentCreateAdvertisementBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() = with(binding) {
        toolbar.setOnLeftIconClick {
            findNavController().navigateUp()
        }
    }
}
