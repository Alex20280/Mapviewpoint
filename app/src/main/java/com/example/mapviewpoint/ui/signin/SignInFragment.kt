package com.example.mapviewpoint.ui.signin

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.mapviewpoint.R
import com.example.mapviewpoint.app.App
import com.example.mapviewpoint.extentions.hideKeyboard
import com.example.mapviewpoint.extentions.openScreen
import com.example.mapviewpoint.extentions.viewBinding
import com.example.mapviewpoint.databinding.FragmentSignInBinding
import com.example.mapviewpoint.extentions.isEmailAndPasswordValid
import com.example.mapviewpoint.extentions.onTextChanged
import com.example.mapviewpoint.network.RequestResult
import javax.inject.Inject

class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val binding by viewBinding(FragmentSignInBinding::bind)

    @Inject
    lateinit var signInViewModel: SignInViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        injectDependencies()
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
                    hideProgressbar()
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
        binding.editTextEmail.onTextChanged {
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            binding.button.isEnabled = isEmailAndPasswordValid(email, password)
            if (email.isEmpty() || password.isEmpty()) {
                binding.button.isEnabled = false
            }
            updateButtonState()
        }

        binding.editTextPassword.onTextChanged {
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            binding.button.isEnabled = isEmailAndPasswordValid(email, password)
            if (email.isEmpty() || password.isEmpty()) {
                binding.button.isEnabled = false
            }
            updateButtonState()
        }
    }

    private fun updateButtonState() {
        if (binding.button.isEnabled) {
            binding.button.setBackgroundColor(binding.button.context.getColor(R.color.colorAccent))
        } else {
            binding.button.setBackgroundColor(binding.button.context.getColor(R.color.grey))
        }
    }

    private fun observeSubmitClick() {
        binding.button.setOnClickListener {
            showProgressbar()
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

    private fun showProgressbar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressbar() {
        binding.progressBar.visibility = View.GONE
    }

    private fun injectDependencies() {
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