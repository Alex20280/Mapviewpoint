package com.example.mapviewpoint.di

import com.example.mapviewpoint.repository.AuthenticationRepositoryImpl
import com.example.mapviewpoint.usecase.UserRegistrationUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides

@Module
object FirebaseRegistrationModule {
    @Provides
    fun provideFirebaseRegistrationUseCase(authenticationRepositoryImpl: AuthenticationRepositoryImpl): UserRegistrationUseCase {
        return UserRegistrationUseCase(authenticationRepositoryImpl)
    }

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }
}