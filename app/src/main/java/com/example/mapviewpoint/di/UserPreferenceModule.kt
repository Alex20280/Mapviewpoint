package com.example.mapviewpoint.di

import android.content.Context
import com.example.mapviewpoint.prefs.UserPreferences
import com.example.mapviewpoint.repository.LocationTrackerRepository
import com.example.mapviewpoint.repository.LocationTrackerRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
object UserPreferenceModule {

    @Provides
    fun provideUserPreferences(context: Context): UserPreferences {
        return UserPreferences(context)
    }
}