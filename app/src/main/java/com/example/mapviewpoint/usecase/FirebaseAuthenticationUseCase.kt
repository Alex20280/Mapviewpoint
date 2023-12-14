package com.example.mapviewpoint.usecase

import com.example.mapviewpoint.network.ErrorDto
import com.example.mapviewpoint.network.RequestResult
import com.example.mapviewpoint.repository.AuthenticationRepositoryImpl
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class FirebaseAuthenticationUseCase @Inject constructor(
    private val authenticationRepositoryImpl: AuthenticationRepositoryImpl

) {

    fun registerUser(email: String, password: String) : RequestResult<Task<AuthResult>>{
        return authenticationRepositoryImpl.registerUser(email, password)
    }


    fun loginUser(email: String, password: String): RequestResult<Task<AuthResult>> {
        return authenticationRepositoryImpl.loginUser(email, password)
    }

    fun resetPassword(email: String): Task<RequestResult<Unit>> {
        return authenticationRepositoryImpl.resetPassword(email)
    }

    fun getUserUd(): String? {
        return authenticationRepositoryImpl.getUserUd()
    }
}