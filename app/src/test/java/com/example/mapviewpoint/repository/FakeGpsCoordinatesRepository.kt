package com.example.mapviewpoint.repository

import com.example.mapviewpoint.model.GpsCoordinates

class FakeGpsCoordinatesRepository: GpsCoordinatesRepository() {

    private val coordinateList = mutableListOf<GpsCoordinates>()

    override suspend fun getCoordinatesByTime(time: Long): List<GpsCoordinates> {
        return coordinateList
    }

    override suspend fun getCoordinatesLast24Hours(): List<GpsCoordinates> {
       return coordinateList
    }
}