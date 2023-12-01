package com.example.mapviewpoint.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mapviewpoint.model.GpsCoordinates
import com.example.mapviewpoint.prefs.UserPreferences
import com.example.mapviewpoint.repository.GpsFirebaseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class MapViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
    private val gpsFirebaseRepository: GpsFirebaseRepository
) : ViewModel() {

    fun getUserId(): String {
        return userPreferences.getUserId().toString()
    }

    suspend fun getGpsCoordinatesByTime(time: Long): List<GpsCoordinates>  {
        val deferredCoordinates: Deferred<List<GpsCoordinates>> = viewModelScope.async {
            gpsFirebaseRepository.getCoordinatesByTime(time)
        }

        return deferredCoordinates.await()
    }

    suspend fun getGpsCoordinatesByLast24Hours(time: Long): List<GpsCoordinates> {
        val deferredCoordinates: Deferred<List<GpsCoordinates>> = viewModelScope.async {
            gpsFirebaseRepository.getCoordinatesByTime(time)
        }

        return deferredCoordinates.await()
    }
}