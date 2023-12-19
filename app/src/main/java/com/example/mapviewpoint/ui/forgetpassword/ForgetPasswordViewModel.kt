package com.example.mapviewpoint.ui.forgetpassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mapviewpoint.network.RequestResult
import com.example.mapviewpoint.usecase.ResetPasswordUseCase
import com.example.mapviewpoint.usecase.UserRegistrationUseCase
import com.google.android.gms.tasks.Task
import javax.inject.Inject

class ForgetPasswordViewModel @Inject constructor(
    private val resetPasswordUseCase: ResetPasswordUseCase
) : ViewModel() {

    private val resetPasswordResult = MutableLiveData<RequestResult<Unit>>()
    fun getResetPasswordResultLiveData(): LiveData<RequestResult<Unit>> {
        return resetPasswordResult
    }

    fun resetPassword(email: String){
        val response = resetPasswordUseCase.resetPassword(email)
        checkEmailResponse(response)
    }

    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun checkEmailResponse(response:Task<RequestResult<Unit>>) {
        response.addOnCompleteListener { task ->
            val result = task.result
            when (result) {
                is RequestResult.Success -> {
                    resetPasswordResult.value = result
                }
                is RequestResult.Error -> {
                    resetPasswordResult.value = RequestResult.Error(result.errorData, result.code)
                }
                is RequestResult.Loading -> Unit
            }
        }
    }
}