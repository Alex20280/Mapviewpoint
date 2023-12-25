package com.example.mapviewpoint.di

import android.content.Context
import com.example.mapviewpoint.repository.AuthenticationRepositoryImpl
import com.example.mapviewpoint.repository.GpsCoordinatesRepository
import com.example.mapviewpoint.repository.GpsCoordinatesRepositoryImpl
import com.example.mapviewpoint.repository.LocationTrackerRepository
import com.example.mapviewpoint.repository.LocationTrackerRepositoryImpl
import com.example.mapviewpoint.usecase.GetDailyLocationCoordinateUseCase
import com.example.mapviewpoint.usecase.UserRegistrationUseCase
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

