package com.example.mapviewpoint.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mapviewpoint.network.RequestResult
import com.example.mapviewpoint.usecase.UserRegistrationUseCase
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
    private val registrationUseCase: UserRegistrationUseCase
) : ViewModel() {

    private val signUpResult = MutableLiveData<RequestResult<Task<AuthResult>>>()
    fun getSignUpResultLiveData(): LiveData<RequestResult<Task<AuthResult>>> {
        return signUpResult
    }

    fun registerUser(email: String, password: String) {
        viewModelScope.launch {
            val response = registrationUseCase.registerUser(email, password)
            checkSignUpResponse(response)
        }
    }

    private fun checkSignUpResponse(response: RequestResult<Task<AuthResult>>) {
        when (response) {
            is RequestResult.Success -> {
                signUpResult.postValue(response)
            }
            is RequestResult.Error -> {
                signUpResult.postValue(RequestResult.Error(response.errorData, response.code))
            }
            is RequestResult.Loading -> Unit
        }
    }
}