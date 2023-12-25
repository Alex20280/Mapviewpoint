package com.example.mapviewpoint.di

import com.example.mapviewpoint.repository.AuthenticationRepository
import com.example.mapviewpoint.repository.AuthenticationRepositoryImpl
import com.example.mapviewpoint.repository.GpsCoordinatesRepository
import com.example.mapviewpoint.repository.GpsCoordinatesRepositoryImpl
import com.example.mapviewpoint.repository.LocationTrackerRepository
import com.example.mapviewpoint.repository.LocationTrackerRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
object RepositoryModule {

    @Provides
    fun provideGpsRepository(impl: GpsCoordinatesRepositoryImpl): GpsCoordinatesRepository {
        return impl
    }

    @Provides
    fun provideLocationTrackerRepository(impl: LocationTrackerRepositoryImpl): LocationTrackerRepository {
        return impl
    }
    @Provides
    fun provideAuthenticationRepository(impl: AuthenticationRepositoryImpl): AuthenticationRepository {
        return impl
    }
}