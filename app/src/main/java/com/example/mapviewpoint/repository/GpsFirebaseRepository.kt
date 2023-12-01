package com.example.mapviewpoint.repository

import com.example.mapviewpoint.model.GpsCoordinates
import com.example.mapviewpoint.prefs.UserPreferences
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class GpsFirebaseRepository @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase,
    private val userPreferences: UserPreferences
) {

    suspend fun getCoordinatesByTime(time: Long): List<GpsCoordinates> {
        val userId = userPreferences.getUserId().toString()
        val reference: DatabaseReference = firebaseDatabase.getReference("locations/$userId")

        return suspendCoroutine { continuation ->
            reference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val coordinatesList = mutableListOf<GpsCoordinates>()
                    for (coordinateSnapshot in snapshot.children) {
                        val latitude =
                            coordinateSnapshot.child("latitude").getValue(Double::class.java)
                        val longitude =
                            coordinateSnapshot.child("longitude").getValue(Double::class.java)
                        val timestamp =
                            coordinateSnapshot.child("timestamp").getValue(Long::class.java)

                        if (latitude != null && longitude != null && timestamp != null && timestamp == time) {
                            val coordinates = GpsCoordinates(latitude, longitude, timestamp)
                            coordinatesList.add(coordinates)
                        }
                    }

                    if (coordinatesList.isEmpty()) {
                        continuation.resumeWithException(IllegalStateException("Coordinates are missing for user $userId at time: $time"))
                    } else {
                        continuation.resume(coordinatesList)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    continuation.resumeWithException(error.toException())
                }
            })
        }
    }
}