package com.suatzengin.iloveanimals.ui.auth.register

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.suatzengin.iloveanimals.R
import com.suatzengin.iloveanimals.core.ui.snackbar.SnackbomType
import com.suatzengin.iloveanimals.core.viewbinding.viewBinding
import com.suatzengin.iloveanimals.databinding.FragmentRegisterBinding
import com.suatzengin.iloveanimals.util.extension.showSnackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {
    private val binding by viewBinding(FragmentRegisterBinding::bind)
    private val viewModel by viewModels<RegisterViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        collectData()
    }

    private fun initView() = with(binding) {
        editTextChangeListeners()

        buttonRegister.setOnClickListener {
            viewModel.register()
        }
    }

    private fun editTextChangeListeners() {
        binding.apply {
            etFullName.addTextChangedListener {
                viewModel.setFullName(fullName = it.toString())
            }

            etEmail.addTextChangedListener {
                viewModel.setEmail(email = it.toString())
            }

            etPassword.addTextChangedListener {
                viewModel.setPassword(password = it.toString())
            }

            etConfirmPassword.addTextChangedListener {
                viewModel.setConfirmPassword(confirmPassword = it.toString())
            }

            etPhoneNumber.addTextChangedListener {
                viewModel.setPhoneNumber(phoneNumber = it.toString())
            }
        }
    }

    private fun collectData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                viewModel.uiEvent.collectLatest { event ->
                    when (event) {
                        is RegisterUiEvent.Error -> {
                            showSnackbar(type = SnackbomType.ERROR, text = event.message)
                        }
                        RegisterUiEvent.NavigateToLogin -> {
                            findNavController().popBackStack()
                        }
                        is RegisterUiEvent.PasswordNotConfirmed -> {
                            showSnackbar(type = SnackbomType.ERROR, text = event.message)
                        }
                    }
                }
            }
        }
    }
}
