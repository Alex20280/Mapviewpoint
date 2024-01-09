package com.example.mapviewpoint.di

import android.content.Context
import com.example.mapviewpoint.prefs.UserPreferences
import dagger.Module
import dagger.Provides

@Module
object UserPreferenceModule {

    @Provides
    fun provideUserPreferences(context: Context): UserPreferences {
        return UserPreferences(context)
    }
}