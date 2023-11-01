package com.suatzengin.iloveanimals.ui.advertisement

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.suatzengin.iloveanimals.R
import com.suatzengin.iloveanimals.databinding.FragmentAdvertisementListBinding
import com.suatzengin.iloveanimals.core.viewbinding.viewBinding

class AdvertisementListFragment : Fragment(R.layout.fragment_advertisement_list) {
    private val binding by viewBinding(FragmentAdvertisementListBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}