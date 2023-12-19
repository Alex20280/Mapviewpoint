package com.example.mapviewpoint.usecase

import com.example.mapviewpoint.model.GpsCoordinates
import com.example.mapviewpoint.repository.GpsCoordinatesRepositoryImpl
import javax.inject.Inject

class GetDailyLocationCoordinateUseCase @Inject constructor(
    private val gpsFirebaseRepositoryImpl: GpsCoordinatesRepositoryImpl
) {
    suspend fun getCoordinatesWithin24Hours(): List<GpsCoordinates> {
        return gpsFirebaseRepositoryImpl.getCoordinatesLast24Hours()
    }
}