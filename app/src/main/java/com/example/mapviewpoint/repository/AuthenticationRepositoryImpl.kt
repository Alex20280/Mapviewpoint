package com.example.mapviewpoint.repository

import com.example.mapviewpoint.network.ErrorDto
import com.example.mapviewpoint.network.RequestResult
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthenticationRepository(){
    override fun registerUser(email: String, password: String): RequestResult<Task<AuthResult>> {
        try {
            val authResultTask = auth.createUserWithEmailAndPassword(email, password)
            return RequestResult.Success(authResultTask)
        } catch (e: Exception) {
            // Handle exceptions here
            return RequestResult.Error(ErrorDto.Default("Registration problem"), 0)
        }
    }

    override fun loginUser(email: String, password: String): RequestResult<Task<AuthResult>> {
        try {
            val authResultTask = auth.signInWithEmailAndPassword(email, password)
            return RequestResult.Success(authResultTask)
        } catch (e: Exception) {
            // Handle exceptions here
            return RequestResult.Error(ErrorDto.Default("Login problem"), 0)
        }
    }

    override fun resetPassword(email: String): Task<RequestResult<Unit>> {
        val resetResultTask = auth.sendPasswordResetEmail(email)
        return resetResultTask.continueWith { task ->
            if (task.isSuccessful) {
                RequestResult.Success(Unit)
            } else {
                RequestResult.Error(ErrorDto.Default("Reset problem"), 0)
            }
        }
    }

    override fun getUserUd(): String? {
        return auth.uid
    }
}