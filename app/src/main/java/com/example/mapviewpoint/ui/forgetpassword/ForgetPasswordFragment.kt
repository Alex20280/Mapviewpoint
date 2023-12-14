package com.example.mapviewpoint.ui.forgetpassword

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import com.example.mapviewpoint.R
import com.example.mapviewpoint.app.App
import com.example.mapviewpoint.base.checkFieldsForButtonColor
import com.example.mapviewpoint.base.hideKeyboard
import com.example.mapviewpoint.base.openScreen
import com.example.mapviewpoint.base.viewBinding
import com.example.mapviewpoint.databinding.FragmentForgetPasswordBinding
import com.example.mapviewpoint.network.RequestResult
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.launch
import javax.inject.Inject

class ForgetPasswordFragment : Fragment(R.layout.fragment_forget_password) {

    private val binding by viewBinding(FragmentForgetPasswordBinding::bind)

    @Inject
    lateinit var forgetPasswordViewModel: ForgetPasswordViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelInstantiation()
        editTextChangeListener()
        observeSubmitClick()
        observePasswordResetResult()
    }

    private fun viewModelInstantiation() {
        (requireContext().applicationContext as App).appComponent.inject(this)
    }

    private fun editTextChangeListener() {
        binding.resetPasswordEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                checkFieldsForButtonColor(binding.resetPasswordEt, binding.resetPasswordEt, binding.submitBtn)
            }
        })

    }

    private fun observeSubmitClick() {
        binding.submitBtn.setOnClickListener {
            hideKeyboard()
            val email: String = binding.resetPasswordEt.text?.toString() ?: ""

            if (forgetPasswordViewModel.isValidEmail(email)) {
                forgetPasswordViewModel.resetPassword(email)
            } else {
                // Show error messages or UI feedback for invalid input
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
                    Toast.makeText(context, "Login Error", Toast.LENGTH_LONG).show()
                }

                is RequestResult.Loading -> Unit
            }
        }
    }

    private fun navigateToSignInPage() {
        openScreen(ForgetPasswordFragmentDirections.actionForgetPasswordFragmentToSignInFragment())
    }



}