package com.example.mapviewpoint.repository

import com.google.android.gms.maps.model.LatLng

abstract class LocationTrackerRepository {

    abstract suspend fun getCurrentLocation(): LatLng
}