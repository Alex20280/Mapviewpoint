package com.example.mapviewpoint.ui.signin

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mapviewpoint.R
import com.example.mapviewpoint.app.App
import com.example.mapviewpoint.base.openScreen
import com.example.mapviewpoint.databinding.FragmentMapBinding
import com.example.mapviewpoint.databinding.FragmentSignInBinding
import com.example.mapviewpoint.di.ViewModelFactory
import com.example.mapviewpoint.network.RequestResult
import com.google.android.material.internal.ViewUtils.hideKeyboard
import javax.inject.Inject

class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val binding by viewBinding(FragmentSignInBinding::bind)

    @Inject
    lateinit var signInViewModel: SignInViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelInstanciation()
        navigateToSignUpPage()
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

    private fun observeSubmitClick() {
        binding.button.setOnClickListener {
            hideKeyboard()
            val email: String = binding.editTextEmail.text?.toString() ?: ""
            val password: String = binding.editTextPassword.text?.toString() ?: ""

            if (signInViewModel!!.isValidEmail(email) && signInViewModel!!.isValidPassword(password)) {
                signInViewModel?.loginUser(email, password)
            } else {
                // Show error messages or UI feedback for invalid input
                if (signInViewModel!!.isValidEmail(email)) {
                    binding.editTextEmail.error = getString(R.string.invalid_email_warning)
                }
                if (!signInViewModel!!.isValidPassword(password)) {
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

    fun Fragment.hideKeyboard() {
        val activity = requireActivity()
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = activity.currentFocus
        if (view != null) {
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}