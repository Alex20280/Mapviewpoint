package com.example.mapviewpoint.repository

import com.example.mapviewpoint.model.GpsCoordinates
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import java.time.LocalDateTime
import java.time.ZoneOffset
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

abstract class GpsFirebaseRepository {

    abstract suspend fun getCoordinatesByTime(time: Long): List<GpsCoordinates>

    abstract suspend fun getCoordinatesLast24Hours(): List<GpsCoordinates>

}