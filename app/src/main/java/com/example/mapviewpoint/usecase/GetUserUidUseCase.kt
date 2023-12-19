package com.example.mapviewpoint.usecase

import com.example.mapviewpoint.repository.AuthenticationRepositoryImpl
import javax.inject.Inject

class GetUserUidUseCase @Inject constructor(
    private val authenticationRepositoryImpl: AuthenticationRepositoryImpl

) {

    fun getUserUid(): String? {
        return authenticationRepositoryImpl.getUserUd()
    }
}