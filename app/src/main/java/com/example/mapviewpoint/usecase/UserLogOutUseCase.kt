package com.example.mapviewpoint.usecase

import com.example.mapviewpoint.network.RequestResult
import com.example.mapviewpoint.repository.AuthenticationRepositoryImpl
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import javax.inject.Inject

class UserLogOutUseCase @Inject constructor(
    private val authenticationRepositoryImpl: AuthenticationRepositoryImpl
){
    suspend fun userLogout(): RequestResult<Unit> {
        return authenticationRepositoryImpl.logout()
    }
}