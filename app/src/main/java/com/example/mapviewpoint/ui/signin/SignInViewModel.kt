package com.example.mapviewpoint.ui.signin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mapviewpoint.network.RequestResult
import com.example.mapviewpoint.prefs.UserPreferences
import com.example.mapviewpoint.usecase.GetUserUidUseCase
import com.example.mapviewpoint.usecase.UserLoginUseCase
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignInViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
    private val userLoginUseCase: UserLoginUseCase,
    private val getUserUidUseCase: GetUserUidUseCase
) : ViewModel() {

    private val signInResult = MutableLiveData<RequestResult<Task<AuthResult>>>()
    fun getSignInResultLiveData(): LiveData<RequestResult<Task<AuthResult>>> {
        return signInResult
    }

    private val userId = MutableLiveData<String>()
    fun getUserId(): LiveData<String> {
        return userId
    }

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            val response = userLoginUseCase.loginUser(email, password)
            Log.d("logCred", email + password)
            checkSignInResponse(response)
        }
    }

    private fun checkSignInResponse(response: RequestResult<Task<AuthResult>>) {
        when (response) {
            is RequestResult.Success -> {
                signInResult.value = response
                saveUserId(getUserUidUseCase.getUserUid().toString())
            }
            is RequestResult.Error -> {
                signInResult.postValue(RequestResult.Error(response.errorData, response.code))
            }
            is RequestResult.Loading -> Unit
        }
    }

    fun saveUserId(id: String) {
        userPreferences.setUserId(id)
    }

    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(password: String): Boolean {
        // Password should be at least 8 characters long
        if (password.length < 8) {
            return false
        }

        // Check for at least one uppercase letter
        val uppercasePattern = "[A-Z]".toRegex()
        if (!uppercasePattern.containsMatchIn(password)) {
            return false
        }

        // Check for at least one digit
        val digitPattern = "\\d".toRegex()
        if (!digitPattern.containsMatchIn(password)) {
            return false
        }

        // Check for at least one special character
        val specialCharacterPattern = "[!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]".toRegex()
        if (!specialCharacterPattern.containsMatchIn(password)) {
            return false
        }
        return true
    }
}