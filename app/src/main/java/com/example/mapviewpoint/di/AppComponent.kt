package com.example.mapviewpoint.di

import com.example.mapviewpoint.MainActivity
import com.example.mapviewpoint.SharedViewModel
import com.example.mapviewpoint.ui.forgetpassword.ForgetPasswordFragment
import com.example.mapviewpoint.ui.map.MapFragment
import com.example.mapviewpoint.ui.signin.SignInFragment
import com.example.mapviewpoint.ui.signup.SignUpFragment
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        AppModule::class,
        ViewModelModule::class,
        ViewModelModule::class,
        FirebaseDatabaseModule::class,
        FirebaseRegistrationModule::class,
        LocationServiceModule::class,
        RepositoryModule::class,
        UserPreferenceModule::class]
)
@Singleton
interface AppComponent {
    fun inject(mainActivity: MainActivity?)
    fun inject(forgetPasswordFragment: ForgetPasswordFragment?)
    fun inject(signInFragment: SignInFragment?)
    fun inject(signUpFragment: SignUpFragment?)
    fun inject(mapFragment: MapFragment?)
}
