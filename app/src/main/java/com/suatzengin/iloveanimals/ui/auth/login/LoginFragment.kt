package com.suatzengin.iloveanimals.ui.auth.login

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
import com.suatzengin.iloveanimals.data.auth.IlaAuthHandler
import com.suatzengin.iloveanimals.databinding.FragmentLoginBinding
import com.suatzengin.iloveanimals.util.extension.showSnackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {
    private val binding by viewBinding(FragmentLoginBinding::bind)
    private val viewModel by viewModels<LoginViewModel>()

    @Inject
    lateinit var authHandler: IlaAuthHandler

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        collectData()
    }

    private fun initViews() = with(binding) {
        buttonGoToRegister.setOnClickListener {
            findNavController().navigate(R.id.to_registerFragment)
        }

        etEmail.addTextChangedListener {
            viewModel.setEmail(it.toString())
        }
        etPassword.addTextChangedListener {
            viewModel.setPassword(it.toString())
        }

        buttonLogin.setOnClickListener {
            viewModel.login()
        }
    }


    private fun collectData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                viewModel.uiEvent.collectLatest { event ->
                    when (event) {
                        is LoginUiEvent.Error -> {
                            showSnackbar(type = SnackbomType.ERROR, text = event.message)
                        }

                        is LoginUiEvent.NavigateToHome -> {
                            authHandler.saveJWT(event.token)
                            findNavController().navigate(R.id.to_advertisementGraph)
                        }
                    }
                }
            }
        }
    }
}
