package com.example.mapviewpoint.usecase

import com.example.mapviewpoint.model.GpsCoordinates
import com.example.mapviewpoint.repository.GpsCoordinatesRepositoryImpl
import javax.inject.Inject

class GetLocationCoordinatesInTimeRangeUseCase @Inject constructor(
    private val gpsFirebaseRepositoryImpl: GpsCoordinatesRepositoryImpl
) {

    suspend fun getCoordinatesInTimeRange(time: Long): List<GpsCoordinates>{
        return gpsFirebaseRepositoryImpl.getCoordinatesByTime(time)
    }




}