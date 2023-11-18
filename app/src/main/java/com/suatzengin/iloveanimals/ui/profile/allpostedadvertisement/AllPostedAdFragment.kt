package com.suatzengin.iloveanimals.ui.profile.allpostedadvertisement

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.suatzengin.iloveanimals.R
import com.suatzengin.iloveanimals.core.ui.MarginItemDecoration
import com.suatzengin.iloveanimals.core.viewbinding.viewBinding
import com.suatzengin.iloveanimals.databinding.FragmentAllPostedAdBinding
import com.suatzengin.iloveanimals.domain.model.advertisement.Advertisement
import com.suatzengin.iloveanimals.ui.adapter.AdvertisementListAdapter

class AllPostedAdFragment : Fragment(R.layout.fragment_all_posted_ad) {
    private val binding by viewBinding(FragmentAllPostedAdBinding::bind)

    private var advertisementAdapter: AdvertisementListAdapter? = null

    private val args by navArgs<AllPostedAdFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        initView()
    }

    private fun initView() {
        val advertisementList = args.advertisementList

        advertisementAdapter?.submitList(advertisementList.toList())

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupRecyclerView() = with(binding) {
        advertisementAdapter = AdvertisementListAdapter(::onAdvertisementClick)

        rvAllPostedAdvertisements.apply {
            layoutManager = LinearLayoutManager(requireContext())

            addItemDecoration(MarginItemDecoration())

            adapter = advertisementAdapter
        }
    }

    private fun onAdvertisementClick(advertisement: Advertisement) {
        // no-op for now
    }
}
