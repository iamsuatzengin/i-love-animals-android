package com.suatzengin.iloveanimals.ui.advertisement

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.suatzengin.iloveanimals.R
import com.suatzengin.iloveanimals.core.viewbinding.viewBinding
import com.suatzengin.iloveanimals.databinding.FragmentAdvertisementListBinding
import com.suatzengin.iloveanimals.ui.advertisement.adapter.AdvertisementAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AdvertisementListFragment : Fragment(R.layout.fragment_advertisement_list) {
    private val binding by viewBinding(FragmentAdvertisementListBinding::bind)
    private val viewModel by viewModels<AdvertisementViewModel>()

    private val adapter by lazy { AdvertisementAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etSearch.setOnEndIconClick {
            Toast.makeText(requireContext(), "Filter - Tıklandı", Toast.LENGTH_SHORT).show()
        }

        setupRecyclerView()
        collectData()

    }

    private fun setupRecyclerView() {
        val recyclerView = binding.rvAdvertisement
        val layoutManager = LinearLayoutManager(requireContext())

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    private fun collectData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { list ->
                    if(list.isEmpty()) {
                        Log.i("network - fragment", "Liste boş")
                    }

                    adapter.submitList(list)
                    Log.i("network - fragment", "Liste geldi: $list")

                }
            }
        }
    }
}