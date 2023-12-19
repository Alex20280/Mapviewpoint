package com.example.mapviewpoint.ui.signin

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import com.example.mapviewpoint.R
import com.example.mapviewpoint.app.App
import com.example.mapviewpoint.base.checkFieldsForButtonColor
import com.example.mapviewpoint.base.hideKeyboard
import com.example.mapviewpoint.base.openScreen
import com.example.mapviewpoint.base.viewBinding
import com.example.mapviewpoint.databinding.FragmentSignInBinding
import com.example.mapviewpoint.network.RequestResult
import javax.inject.Inject

class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val binding by viewBinding(FragmentSignInBinding::bind)

    @Inject
    lateinit var signInViewModel: SignInViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelInstanciation()
        navigateToSignUpPage()
        editTextChangeListener()
        navigateToForgetPasswordScreen()
        observeSubmitClick()
        observeLogin()
    }

    private fun observeLogin() {
        signInViewModel.getSignInResultLiveData().observe(viewLifecycleOwner) { result ->
            when (result) {
                is RequestResult.Success -> {
                    navigateToMapPage()
                    //signInViewModel!!.saveUserId(result.data.result.user?.uid.toString())
                   // Log.d("userIdKey", signInViewModel!!.getSomeGetUserId())
                }

                is RequestResult.Error -> {
                    Toast.makeText(context, "Login Error", Toast.LENGTH_LONG).show()
                }

                is RequestResult.Loading -> Unit
            }
        }

    }

    private fun editTextChangeListener() {
        binding.editTextEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                checkFieldsForButtonColor(binding.editTextEmail, binding.editTextPassword, binding.button)
            }
        })

        binding.editTextPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                checkFieldsForButtonColor(binding.editTextEmail, binding.editTextPassword, binding.button)
            }
        })
    }

    private fun observeSubmitClick() {
        binding.button.setOnClickListener {
            hideKeyboard()
            val email: String = binding.editTextEmail.text?.toString() ?: ""
            val password: String = binding.editTextPassword.text?.toString() ?: ""

            if (signInViewModel.isValidEmail(email) && signInViewModel.isValidPassword(password)) {
                signInViewModel.loginUser(email, password)
            } else {
                // Show error messages or UI feedback for invalid input
                if (signInViewModel.isValidEmail(email)) {
                    binding.editTextEmail.error = getString(R.string.invalid_email_warning)
                }
                if (!signInViewModel.isValidPassword(password)) {
                    binding.editTextPassword.error = getString(R.string.invalid_password_warning)
                }
            }

        }
    }


    private fun viewModelInstanciation() {
        (requireContext().applicationContext as App).appComponent.inject(this)
     }


    private fun navigateToSignUpPage() {
        binding.textViewSignUp.setOnClickListener {
            openScreen(SignInFragmentDirections.actionSignInFragmentToSignUpFragment())
        }
    }

    private fun navigateToMapPage() {
        openScreen(SignInFragmentDirections.actionSignInFragmentToMapFragment())
    }

    private fun navigateToForgetPasswordScreen() {
        binding.textViewForgotPassword.setOnClickListener {
            openScreen(SignInFragmentDirections.actionSignInFragmentToForgetPasswordFragment())
        }
    }

}