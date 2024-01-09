package com.example.mapviewpoint.di

import android.app.Application
import android.content.Context
import com.example.mapviewpoint.app.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: App) {

    @Provides
    @Singleton
    fun provideApplication(): Application {
        return application
    }

    @Provides
    @Singleton
    fun provideApplicationContext(application: Application): Context {
        return application.applicationContext
    }
}