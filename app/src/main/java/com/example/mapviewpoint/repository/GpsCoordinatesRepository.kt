package com.example.mapviewpoint.repository

import com.example.mapviewpoint.model.GpsCoordinates

abstract class GpsCoordinatesRepository {

    abstract suspend fun getCoordinatesByTime(time: Long): List<GpsCoordinates>

    abstract suspend fun getCoordinatesLast24Hours(): List<GpsCoordinates>

}