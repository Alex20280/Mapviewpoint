package com.example.mapviewpoint.usecase

import com.example.mapviewpoint.model.GpsCoordinates
import com.example.mapviewpoint.repository.GpsFirebaseRepositoryImpl
import javax.inject.Inject

class ReceiveCoordinatesUseCase @Inject constructor(
    private val gpsFirebaseRepositoryImpl: GpsFirebaseRepositoryImpl
) {

    suspend fun getCoordinatesByTime(time: Long): List<GpsCoordinates>{
        return gpsFirebaseRepositoryImpl.getCoordinatesByTime(time)
    }

    suspend fun getCoordinatesLast24Hours(): List<GpsCoordinates> {
        return gpsFirebaseRepositoryImpl.getCoordinatesLast24Hours()
    }


}