package com.suatzengin.iloveanimals.ui.search

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.suatzengin.iloveanimals.R
import com.suatzengin.iloveanimals.core.ui.MarginItemDecoration
import com.suatzengin.iloveanimals.core.viewbinding.viewBinding
import com.suatzengin.iloveanimals.databinding.FragmentSearchBinding
import com.suatzengin.iloveanimals.domain.model.advertisement.Advertisement
import com.suatzengin.iloveanimals.ui.search.adapter.SearchAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private val binding by viewBinding(FragmentSearchBinding::bind)
    private val viewModel by viewModels<SearchViewModel>()

    private val args by navArgs<SearchFragmentArgs>()

    private val adapter by lazy { SearchAdapter(::onSearchItemClick) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        collectData()
        setupRecyclerView()
    }

    private fun initView() = with(binding) {
        etSearch.editText?.setText(args.key)
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.rvSearchedAdvertisement
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(MarginItemDecoration(left = 16, right = 16))
        recyclerView.adapter = adapter
    }

    private fun collectData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    if (uiState.list.isNotEmpty()) {
                        binding.emptyState.isVisible = false
                        binding.rvSearchedAdvertisement.isVisible = true
                        adapter.submitList(uiState.list)
                    }

                    if (uiState.list.isEmpty()) {
                        binding.emptyState.isVisible = true
                        binding.rvSearchedAdvertisement.isVisible = false
                    }
                }
            }
        }
    }

    private fun onSearchItemClick(advertisement: Advertisement) {
        Toast.makeText(requireContext(), "Title: ${advertisement.title}", Toast.LENGTH_SHORT).show()
    }
}