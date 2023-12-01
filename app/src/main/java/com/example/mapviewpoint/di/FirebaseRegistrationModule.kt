package com.example.mapviewpoint.di

import com.example.mapviewpoint.usecase.FirebaseAuthenticationUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides

@Module
object FirebaseRegistrationModule {
    @Provides
    fun provideFirebaseRegistrationUseCase(auth: FirebaseAuth): FirebaseAuthenticationUseCase {
        return FirebaseAuthenticationUseCase(auth)
    }

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }
}