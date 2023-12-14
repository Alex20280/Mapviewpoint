package com.example.mapviewpoint.di

import com.example.mapviewpoint.repository.AuthenticationRepositoryImpl
import com.example.mapviewpoint.usecase.FirebaseAuthenticationUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides

@Module
object FirebaseRegistrationModule {
    @Provides
    fun provideFirebaseRegistrationUseCase(authenticationRepositoryImpl: AuthenticationRepositoryImpl): FirebaseAuthenticationUseCase {
        return FirebaseAuthenticationUseCase(authenticationRepositoryImpl)
    }

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }
}