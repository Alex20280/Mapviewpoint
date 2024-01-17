package com.example.mapviewpoint.usecase

import com.example.mapviewpoint.model.GpsCoordinates
import com.example.mapviewpoint.repository.GpsCoordinatesRepository
import com.example.mapviewpoint.repository.GpsCoordinatesRepositoryImpl
import javax.inject.Inject

class GetDailyLocationCoordinateUseCase @Inject constructor(
    private val gpsFirebaseRepository: GpsCoordinatesRepository
) {
    suspend fun getCoordinatesWithin24Hours(): List<GpsCoordinates> {
        return gpsFirebaseRepository.getCoordinatesLast24Hours()
    }
}