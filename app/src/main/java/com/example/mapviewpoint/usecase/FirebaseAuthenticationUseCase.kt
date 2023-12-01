package com.example.mapviewpoint.usecase

import com.example.mapviewpoint.network.ErrorDto
import com.example.mapviewpoint.network.RequestResult
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class FirebaseAuthenticationUseCase(private val auth: FirebaseAuth) {

    fun registerUser(email: String, password: String): RequestResult<Task<AuthResult>> {
        try {
            val authResultTask = auth.createUserWithEmailAndPassword(email, password)
            return RequestResult.Success(authResultTask)
        } catch (e: Exception) {
            // Handle exceptions here
            return RequestResult.Error(ErrorDto.Default("Registration problem"), 0)
        }
    }

    fun loginUser(email: String, password: String): RequestResult<Task<AuthResult>> {
        try {
            val authResultTask = auth.signInWithEmailAndPassword(email, password)
            return RequestResult.Success(authResultTask)
        } catch (e: Exception) {
            // Handle exceptions here
            return RequestResult.Error(ErrorDto.Default("Login problem"), 0)
        }
    }

    fun resetPassword(email: String): RequestResult<Task<Void>> {
        try {
            val resetResultTask = auth.sendPasswordResetEmail(email)
            return RequestResult.Success(resetResultTask)
        } catch (e: Exception) {
            return RequestResult.Error(ErrorDto.Default("Reset problem"), 0)
        }
    }

    fun getUserUd(): String? {
        return auth.uid
    }
}