package com.example.mapviewpoint.usecase

import com.example.mapviewpoint.repository.LocationTrackerRepositoryImpl
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class CurrentLocationUseCase @Inject constructor(
    private val locationTrackerRepository: LocationTrackerRepositoryImpl
){

    suspend fun getCurrentLocation(): LatLng {
       return locationTrackerRepository.getCurrentLocation()
    }
}