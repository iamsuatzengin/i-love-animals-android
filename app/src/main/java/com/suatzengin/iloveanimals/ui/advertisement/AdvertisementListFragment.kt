package com.suatzengin.iloveanimals.ui.advertisement

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.suatzengin.iloveanimals.R
import com.suatzengin.iloveanimals.core.viewbinding.viewBinding
import com.suatzengin.iloveanimals.databinding.FragmentAdvertisementListBinding

class AdvertisementListFragment : Fragment(R.layout.fragment_advertisement_list) {
    private val binding by viewBinding(FragmentAdvertisementListBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etSearch.setOnEndIconClick {
            Toast.makeText(requireContext(), "Filter - Tıklandı", Toast.LENGTH_SHORT).show()
        }
    }
}