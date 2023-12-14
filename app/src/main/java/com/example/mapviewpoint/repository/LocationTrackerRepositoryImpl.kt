package com.example.mapviewpoint.repository

import android.annotation.SuppressLint
import com.example.mapviewpoint.network.ErrorDto
import com.example.mapviewpoint.network.RequestResult
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class LocationTrackerRepositoryImpl @Inject constructor(
    private val fusedLocationClient: FusedLocationProviderClient
) : LocationTrackerRepository() {

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): LatLng = suspendCancellableCoroutine { cont ->

        val locationTask = fusedLocationClient.lastLocation

        locationTask.addOnSuccessListener { location ->
            if(location != null) {
                val latLng = LatLng(location.latitude, location.longitude)
                cont.resume(latLng)
            }
        }.addOnFailureListener {
            cont.resumeWithException(it)
        }
    }
}