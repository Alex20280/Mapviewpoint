package com.example.mapviewpoint.app

import android.app.Application
import com.example.mapviewpoint.di.AppComponent
import com.example.mapviewpoint.di.AppModule
import com.example.mapviewpoint.di.DaggerAppComponent

class App  : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
       appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}