package com.example.mapviewpoint.ui.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import com.example.mapviewpoint.R
import com.example.mapviewpoint.app.App
import com.example.mapviewpoint.extentions.hideKeyboard
import com.example.mapviewpoint.extentions.openScreen
import com.example.mapviewpoint.extentions.viewBinding
import com.example.mapviewpoint.databinding.FragmentSignUpBinding
import com.example.mapviewpoint.extentions.isEmailAndPasswordValid
import com.example.mapviewpoint.extentions.onTextChanged
import com.example.mapviewpoint.network.RequestResult
import javax.inject.Inject

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private val binding by viewBinding(FragmentSignUpBinding::bind)

    @Inject
    lateinit var signUpViewModel: SignUpViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        injectDependencies()
        editTextChangeListener()
        observeRegistration()
        registerButtonListener()
    }

    private fun injectDependencies() {
        (requireContext().applicationContext as App).appComponent.inject(this)
    }

    private fun editTextChangeListener() {
        binding.emailEt.onTextChanged {
            val email = binding.emailEt.text.toString().trim()
            val password = binding.passwordEt.text.toString().trim()
            if (isEmailAndPasswordValid(email, password)) {
                binding.submitBtn.setBackgroundColor(binding.submitBtn.context.getColor(R.color.colorAccent))
                binding.submitBtn.isEnabled = true
            }
        }

        binding.passwordEt.onTextChanged {
            val email = binding.emailEt.text.toString().trim()
            val password = binding.passwordEt.text.toString().trim()
            if (isEmailAndPasswordValid(email, password)) {
                binding.submitBtn.setBackgroundColor(binding.submitBtn.context.getColor(R.color.colorAccent))
                binding.submitBtn.isEnabled = true
            }
        }
    }

    private fun registerButtonListener() {
        binding.submitBtn.setOnClickListener {
            hideKeyboard()
            val email: String = binding.emailEt.text?.toString() ?: ""
            val password: String = binding.passwordEt.text?.toString() ?: ""

            if (signUpViewModel.isValidEmail(email) && signUpViewModel.isValidPassword(password)) {
                signUpViewModel.registerUser(email, password)
            } else {
                // Show error messages or UI feedback for invalid input
                if (signUpViewModel.isValidEmail(email)) {
                    binding.emailEt.error = getString(R.string.invalid_email_warning)
                }
                if (!signUpViewModel.isValidPassword(password)) {
                    binding.passwordEt.error = getString(R.string.invalid_password_warning)
                }
            }
        }
    }

    private fun observeRegistration() {
        signUpViewModel.getSignUpResultLiveData().observe(viewLifecycleOwner) { result ->
            when (result) {
                is RequestResult.Success -> {
                    openScreen(SignUpFragmentDirections.actionSignUpFragmentToSignInFragment())
                }

                is RequestResult.Error -> {
                    Toast.makeText(context, "", Toast.LENGTH_LONG).show()
                }

                is RequestResult.Loading -> Unit
            }
        }
    }
}