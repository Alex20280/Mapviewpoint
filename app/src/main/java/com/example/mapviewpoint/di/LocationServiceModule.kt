package com.example.mapviewpoint.di

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides

@Module
object LocationServiceModule {

    @Provides
    fun provideFusedLocationProviderClient(context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }
}

