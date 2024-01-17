package com.example.mapviewpoint.usecase

import com.example.mapviewpoint.model.GpsCoordinates
import com.example.mapviewpoint.repository.GpsCoordinatesRepository
import com.example.mapviewpoint.repository.GpsCoordinatesRepositoryImpl
import javax.inject.Inject

class GetLocationCoordinatesInTimeRangeUseCase @Inject constructor(
    private val gpsFirebaseRepository: GpsCoordinatesRepository
) {

    suspend fun getCoordinatesInTimeRange(time: Long): List<GpsCoordinates>{
        return gpsFirebaseRepository.getCoordinatesByTime(time)
    }

}