package com.suatzengin.iloveanimals.ui.confirmadvertisement

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.suatzengin.iloveanimals.R
import com.suatzengin.iloveanimals.core.ui.LoadingDialog
import com.suatzengin.iloveanimals.core.ui.snackbar.SnackbomType
import com.suatzengin.iloveanimals.core.viewbinding.viewBinding
import com.suatzengin.iloveanimals.databinding.FragmentConfirmAdvertisementBinding
import com.suatzengin.iloveanimals.domain.model.advertisement.AdvertisementCategory
import com.suatzengin.iloveanimals.ui.createadvertisement.CreateAdvertisementUiEvent
import com.suatzengin.iloveanimals.ui.createadvertisement.CreateAdvertisementUiState
import com.suatzengin.iloveanimals.util.extension.showSnackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ConfirmAdvertisementFragment : Fragment(R.layout.fragment_confirm_advertisement) {
    private val binding by viewBinding(FragmentConfirmAdvertisementBinding::bind)
    private val viewModel by viewModels<ConfirmAdViewModel>()

    private val loadingDialog: LoadingDialog by lazy {
        LoadingDialog(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        collectData()
    }

    private fun collectData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.loadingState.collectLatest { loadingState ->
                        loadingDialog.showLoading(loadingState)
                    }
                }

                launch {
                    viewModel.uiState.collectLatest {
                        initView(it)
                        Log.i("Tag", "state: $it")
                    }
                }

                launch {
                    viewModel.uiEvent.collectLatest { uiEvent ->
                        handleEvent(uiEvent)
                    }
                }
            }
        }
    }

    private fun initView(uiState: CreateAdvertisementUiState) = with(binding) {
        val category = AdvertisementCategory.entries.first { it.id == uiState.categoryId }

        tvCategory.text = AdvertisementCategory.getTitle(category)
        tvTitle.text = uiState.title
        tvDescription.text = uiState.description
        tvAddress.text = uiState.address

        btnSave.setOnClickListener {
            viewModel.createAdvertisement()
        }
    }

    private fun handleEvent(uiEvent: CreateAdvertisementUiEvent) {
        when (uiEvent) {
            CreateAdvertisementUiEvent.CreatedAdvertisement -> {
                showSnackbar(
                    type = SnackbomType.SUCCESS,
                    text = getString(R.string.text_successfully_added)
                )
                findNavController().popBackStack(
                    R.id.advertisementListFragment, false
                )
            }

            is CreateAdvertisementUiEvent.Error -> {
                showSnackbar(
                    type = SnackbomType.ERROR,
                    text = uiEvent.message
                )
            }
        }
    }
}
