package com.example.mapviewpoint.usecase

import com.example.mapviewpoint.repository.LocationTrackerRepository
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class GetCurrentLocationUseCase @Inject constructor(
    private val locationTrackerRepository: LocationTrackerRepository
){

    suspend fun getCurrentLocation(): LatLng {
       return locationTrackerRepository.getCurrentLocation()
    }
}