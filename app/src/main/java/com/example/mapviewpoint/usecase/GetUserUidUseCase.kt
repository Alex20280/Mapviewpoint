package com.example.mapviewpoint.usecase

import com.example.mapviewpoint.repository.AuthenticationRepository
import com.example.mapviewpoint.repository.AuthenticationRepositoryImpl
import javax.inject.Inject

class GetUserUidUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {

    fun getUserUid(): String? {
        return authenticationRepository.getUserUd()
    }
}