package com.example.mapviewpoint.usecase

import com.example.mapviewpoint.network.RequestResult
import com.example.mapviewpoint.repository.AuthenticationRepositoryImpl
import com.google.android.gms.tasks.Task
import javax.inject.Inject

class ResetPasswordUseCase @Inject constructor(
    private val authenticationRepositoryImpl: AuthenticationRepositoryImpl

) {
    fun resetPassword(email: String): Task<RequestResult<Unit>> {
        return authenticationRepositoryImpl.resetPassword(email)
    }
}