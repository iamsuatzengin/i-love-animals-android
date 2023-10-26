package com.suatzengin.iloveanimals.ui.auth.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.suatzengin.iloveanimals.R
import com.suatzengin.iloveanimals.databinding.FragmentRegisterBinding
import com.suatzengin.iloveanimals.ui.auth.util.viewBinding

class RegisterFragment : Fragment(R.layout.fragment_register) {
    private val binding by viewBinding(FragmentRegisterBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}