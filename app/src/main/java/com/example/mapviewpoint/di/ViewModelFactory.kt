package com.example.mapviewpoint.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mapviewpoint.SharedViewModel
import com.example.mapviewpoint.prefs.UserPreferences
import com.example.mapviewpoint.ui.forgetpassword.ForgetPasswordViewModel
import com.example.mapviewpoint.ui.map.MapViewModel
import com.example.mapviewpoint.ui.signin.SignInViewModel
import com.example.mapviewpoint.ui.signup.SignUpViewModel
import com.example.mapviewpoint.usecase.FirebaseAuthenticationUseCase
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.firebase.auth.FirebaseAuth
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass

@Singleton
class ViewModelFactory @Inject constructor(
    private val providers: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val provider = providers[modelClass]
        return provider?.get() as T ?: throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
    }
}