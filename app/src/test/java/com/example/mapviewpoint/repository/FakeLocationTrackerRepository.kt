package com.example.mapviewpoint.repository

import com.example.mapviewpoint.model.GpsCoordinates
import com.google.android.gms.maps.model.LatLng

class FakeLocationTrackerRepository: LocationTrackerRepository() {

    private val coordinates = LatLng(0.0,0.0)

    override suspend fun getCurrentLocation(): LatLng {
        return coordinates
    }
}