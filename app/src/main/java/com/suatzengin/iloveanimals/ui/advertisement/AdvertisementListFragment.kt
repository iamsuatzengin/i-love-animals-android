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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.suatzengin.iloveanimals.R
import com.suatzengin.iloveanimals.core.ui.MarginItemDecoration
import com.suatzengin.iloveanimals.core.viewbinding.viewBinding
import com.suatzengin.iloveanimals.databinding.FragmentAdvertisementListBinding
import com.suatzengin.iloveanimals.ui.advertisement.adapter.AdvertisementAdapter
import com.suatzengin.iloveanimals.ui.advertisement.adapter.AdvertisementAdapterListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AdvertisementListFragment :
    Fragment(R.layout.fragment_advertisement_list), AdvertisementAdapterListener {
    private val binding by viewBinding(FragmentAdvertisementListBinding::bind)
    private val viewModel by viewModels<AdvertisementViewModel>()

    private val adapter by lazy { AdvertisementAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        collectData()

    }

    private fun setupRecyclerView() {
        val recyclerView = binding.rvAdvertisement
        val layoutManager = LinearLayoutManager(requireContext())

        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = null
        recyclerView.addItemDecoration(MarginItemDecoration())
        recyclerView.adapter = adapter

    }

    private fun collectData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { list ->
                    if (list.isEmpty()) {
                        Log.i("network - fragment", "Liste boş")
                    }

                    adapter.submitList(list)
                    Log.i("network - fragment", "Liste geldi: $list")
                }
            }
        }
    }

    override fun setOnActionDone(text: String) {
        Toast.makeText(context, "text: $text", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.to_searchFragment)
    }

    override fun onFilterButtonClick() {
        Toast.makeText(requireContext(), "Filter button tıklandı", Toast.LENGTH_SHORT).show()
    }

    override fun onNotificationButtonClick() {
        Toast.makeText(requireContext(), "Bildirim button tıklandı", Toast.LENGTH_SHORT).show()
    }
}