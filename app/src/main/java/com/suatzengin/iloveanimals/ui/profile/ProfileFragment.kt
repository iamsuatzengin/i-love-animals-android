package com.suatzengin.iloveanimals.ui.profile

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.suatzengin.iloveanimals.R
import com.suatzengin.iloveanimals.core.ui.MarginItemDecoration
import com.suatzengin.iloveanimals.core.viewbinding.viewBinding
import com.suatzengin.iloveanimals.databinding.FragmentProfileBinding
import com.suatzengin.iloveanimals.domain.model.advertisement.Advertisement
import com.suatzengin.iloveanimals.ui.adapter.AdvertisementListAdapter
import com.suatzengin.iloveanimals.util.extension.navigateDeepLink
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding by viewBinding(FragmentProfileBinding::bind)

    private val viewModel by viewModels<ProfileViewModel>()

    private var advertisementAdapter: AdvertisementListAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect { state ->
                        initView(state)
                    }
                }

                launch {
                    viewModel.uiEvent.collectLatest { event ->
                        when (event) {
                            ProfileUiEvent.Logout -> {
                                findNavController().navigate(R.id.to_loginFragment)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() = with(binding) {
        advertisementAdapter = AdvertisementListAdapter(::onAdvertisementPostedClick)

        rvAdvertisementPosted.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(MarginItemDecoration(left = 0, right = 0))
            adapter = advertisementAdapter
        }
    }

    private fun initView(state: ProfileUiState) = with(binding) {
        progressBar.isVisible = state.isLoading
        clContainer.isVisible = state.isLoading.not()

        state.profileUiModel?.apply {
            tvFullName.text = fullName
            ivUserProfileImage.load(state.profileUiModel.profileImageUrl)
            tvAdvertisementPosted.text = advertisementPostedCount.toString()
            tvAdvertisementCompleted.text = advertisementPostedCompleted.toString()
            tvEmail.text = email

            advertisementAdapter?.submitList(userAdvertisementList.take(3))

            tvSeeAll.setOnClickListener {
                val action =
                    ProfileFragmentDirections.toAllPostedAdFragment(userAdvertisementList.toTypedArray())

                findNavController().navigate(action)
            }

            btnLogout.setOnClickListener {
                viewModel.userLogout()
            }
        }
    }

    private fun onAdvertisementPostedClick(advertisement: Advertisement) {
        findNavController().navigateDeepLink(
            deeplink = "ila://host/advertisementDetail/${advertisement.id}"
        )
    }
}
