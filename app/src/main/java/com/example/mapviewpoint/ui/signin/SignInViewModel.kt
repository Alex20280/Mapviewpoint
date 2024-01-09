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
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignInViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
    private val userLoginUseCase: UserLoginUseCase,
    private val getUserUidUseCase: GetUserUidUseCase
) : ViewModel() {

    private val signInResult = MutableLiveData<RequestResult<AuthResult>>()
    fun getSignInResultLiveData(): LiveData<RequestResult<AuthResult>> {
        return signInResult
    }

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            val response = userLoginUseCase.loginUser(email, password)
            Log.d("logCred", email + password)
            checkSignInResponse(response)
        }
    }

    private fun checkSignInResponse(response: RequestResult<AuthResult>) {
        when (response) {
            is RequestResult.Success -> {
                signInResult.postValue(response)
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
}