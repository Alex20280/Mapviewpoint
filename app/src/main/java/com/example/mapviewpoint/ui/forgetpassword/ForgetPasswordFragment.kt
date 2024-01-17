package com.example.mapviewpoint.ui.forgetpassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import com.example.mapviewpoint.R
import com.example.mapviewpoint.app.App
import com.example.mapviewpoint.extentions.hideKeyboard
import com.example.mapviewpoint.extentions.openScreen
import com.example.mapviewpoint.extentions.viewBinding
import com.example.mapviewpoint.databinding.FragmentForgetPasswordBinding
import com.example.mapviewpoint.extentions.isEmailAndPasswordValid
import com.example.mapviewpoint.extentions.isEmailValid
import com.example.mapviewpoint.extentions.onTextChanged
import com.example.mapviewpoint.network.RequestResult
import javax.inject.Inject

class ForgetPasswordFragment : Fragment(R.layout.fragment_forget_password) {

    private val binding by viewBinding(FragmentForgetPasswordBinding::bind)

    @Inject
    lateinit var forgetPasswordViewModel: ForgetPasswordViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        injectDependencies()
        editTextChangeListener()
        observeSubmitClick()
        observePasswordResetResult()
    }

    private fun injectDependencies() {
        (requireContext().applicationContext as App).appComponent.inject(this)
    }

    private fun editTextChangeListener() {
        binding.resetPasswordEt.onTextChanged {
            val email= binding.resetPasswordEt.text.toString().trim()
            if (isEmailValid(email)){
                binding.submitBtn.setBackgroundColor(binding.submitBtn.context.getColor(R.color.colorAccent))
                binding.submitBtn.isEnabled = true
            }
        }
    }

    private fun observeSubmitClick() {
        binding.submitBtn.setOnClickListener {
            hideKeyboard()
            val email: String = binding.resetPasswordEt.text?.toString() ?: ""

            if (forgetPasswordViewModel.isValidEmail(email)) {
                forgetPasswordViewModel.resetPassword(email)
            } else {
                if (forgetPasswordViewModel.isValidEmail(email)) {
                    binding.resetPasswordEt.error = getString(R.string.invalid_email_warning)
                }
            }
        }
    }

    private fun observePasswordResetResult() {
        forgetPasswordViewModel.getResetPasswordResultLiveData().observe(viewLifecycleOwner) { result ->
            when (result) {
                is RequestResult.Success -> {
                    navigateToSignInPage()
                }
                is RequestResult.Error -> {
                    Toast.makeText(context, getString(R.string.login_error), Toast.LENGTH_LONG).show()
                }
                is RequestResult.Loading -> Unit
            }
        }
    }

    private fun navigateToSignInPage() {
        openScreen(ForgetPasswordFragmentDirections.actionForgetPasswordFragmentToSignInFragment())
    }
}