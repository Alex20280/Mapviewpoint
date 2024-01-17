package com.example.mapviewpoint.usecase

import com.example.mapviewpoint.network.RequestResult
import com.example.mapviewpoint.repository.AuthenticationRepository
import com.example.mapviewpoint.repository.AuthenticationRepositoryImpl
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import javax.inject.Inject

class UserRegistrationUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {

    suspend fun registerUser(email: String, password: String): RequestResult<Task<AuthResult>> {
        return authenticationRepository.registerUser(email, password)
    }

}