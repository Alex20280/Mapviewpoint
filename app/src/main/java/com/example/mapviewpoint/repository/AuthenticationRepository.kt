package com.example.mapviewpoint.repository

import com.example.mapviewpoint.network.ErrorDto
import com.example.mapviewpoint.network.RequestResult
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

abstract class AuthenticationRepository {

    abstract fun registerUser(email: String, password: String): RequestResult<Task<AuthResult>>

    abstract fun loginUser(email: String, password: String): RequestResult<Task<AuthResult>>

    abstract fun resetPassword(email: String): Task<RequestResult<Unit>>

    abstract fun getUserUd(): String?
}