package com.example.mapviewpoint.usecase

import android.annotation.SuppressLint
import android.location.Location
import com.example.mapviewpoint.prefs.UserPreferences
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FirebaseDatabaseReaderUseCase(
    private val firebaseDatabase: FirebaseDatabase,
    private val userPreferences: UserPreferences
) {

/*    fun getDataByUid(onDataLoaded: (DataSnapshot?) -> Unit, onError: (DatabaseError?) -> Unit) {
        // Assuming you have a "users" node in your database
        val locationsRef = firebaseDatabase.reference.child("locations")

        // Create a reference to the data associated with the UID
        val uid: String = userPreferences.getUserId().toString()
        val userUidRef = locationsRef.child(uid)

        userUidRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Data was successfully fetched
                onDataLoaded(dataSnapshot)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Error occurred while fetching data
                onError(databaseError)
            }
        })
    }*/
}