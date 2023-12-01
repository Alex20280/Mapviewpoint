package com.example.mapviewpoint.di

import androidx.lifecycle.ViewModel
import com.example.mapviewpoint.SharedViewModel
import com.example.mapviewpoint.ui.forgetpassword.ForgetPasswordViewModel
import com.example.mapviewpoint.ui.map.MapViewModel
import com.example.mapviewpoint.ui.signin.SignInViewModel
import com.example.mapviewpoint.ui.signup.SignUpViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton
import kotlin.reflect.KClass

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ForgetPasswordViewModel::class)
    abstract fun forgetPasswordViewModel(forgetPasswordViewModel: ForgetPasswordViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SignInViewModel::class)
    abstract fun signInViewModel(signInViewModel: SignInViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SignUpViewModel::class)
    abstract fun signUpViewModel(signUpViewModel: SignUpViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MapViewModel::class)
    abstract fun mapViewModel(mapViewModel: MapViewModel): ViewModel

    @Binds
    @IntoMap
    @Singleton
    @ViewModelKey(SharedViewModel::class)
    abstract fun sharedViewModel(sharedViewModel: SharedViewModel): ViewModel

}

@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)