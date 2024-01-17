package com.example.mapviewpoint.repository

import com.example.mapviewpoint.model.GpsCoordinates
import com.example.mapviewpoint.prefs.UserPreferences
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Calendar
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class GpsCoordinatesRepositoryImpl @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase,
    private val userPreferences: UserPreferences
) : GpsCoordinatesRepository() {

    override suspend fun getCoordinatesByTime(time: Long): List<GpsCoordinates> {

        val userId = userPreferences.getUserId().toString()
        val ref = FirebaseDatabase.getInstance().getReference("locations/$userId")

        return suspendCoroutine { continuation ->

            val coordinatesList = mutableListOf<GpsCoordinates>()

            ref.addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach { timestampSnapshot ->
                        val latitude = timestampSnapshot.child("lat").getValue(Double::class.java)
                        val longitude = timestampSnapshot.child("long").getValue(Double::class.java)
                        val timestamp =  timestampSnapshot.key?.toLongOrNull()
                        val roundedTimeToADay = getRoundedDay(timestamp!!)
                        if (latitude != null && longitude != null && time == roundedTimeToADay) {
                            coordinatesList.add(GpsCoordinates(latitude, longitude, timestamp))
                        }
                    }
                    continuation.resume(coordinatesList)
                }
                override fun onCancelled(error: DatabaseError) {
                    continuation.resumeWithException(error.toException())
                }
            })
        }
    }

    private fun getRoundedDay(timestamp: Long): Long {
        val calendar = Calendar.getInstance().apply { timeInMillis = timestamp }
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        return calendar.timeInMillis
    }


    override suspend fun getCoordinatesLast24Hours(): List<GpsCoordinates> {
        val userId = userPreferences.getUserId().toString()
        val ref: DatabaseReference = firebaseDatabase.getReference("locations/$userId")

        val currentTime = System.currentTimeMillis()
        val lastDay = getRoundedDay(currentTime)

        return suspendCoroutine { continuation ->
            val coordinatesList = mutableListOf<GpsCoordinates>()
            ref.addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach { timestampSnapshot ->
                        val latitude = timestampSnapshot.child("lat").getValue(Double::class.java)
                        val longitude = timestampSnapshot.child("long").getValue(Double::class.java)
                        val timestamp =  timestampSnapshot.key?.toLongOrNull()
                        val roundedTimeToADay = getRoundedDay(timestamp!!)

                        if (latitude != null && longitude != null && lastDay == roundedTimeToADay) {
                            coordinatesList.add(GpsCoordinates(latitude, longitude, timestamp))
                        }
                    }
                    continuation.resume(coordinatesList)
                }
                override fun onCancelled(error: DatabaseError) {
                    continuation.resumeWithException(error.toException())
                }
            })
        }
    }
}