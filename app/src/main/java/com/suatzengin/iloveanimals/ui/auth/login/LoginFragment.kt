package com.suatzengin.iloveanimals.ui.auth.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.suatzengin.iloveanimals.R
import com.suatzengin.iloveanimals.databinding.FragmentLoginBinding
import com.suatzengin.iloveanimals.ui.auth.util.viewBinding

class LoginFragment : Fragment(R.layout.fragment_login) {
    private val binding by viewBinding(FragmentLoginBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() = with(binding) {
        buttonGoToRegister.setOnClickListener {
            findNavController().navigate(R.id.to_registerFragment)
        }
    }
}