package com.example.mapviewpoint.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mapviewpoint.model.GpsCoordinates
import com.example.mapviewpoint.network.RequestResult
import com.example.mapviewpoint.prefs.UserPreferences
import com.example.mapviewpoint.repository.GpsFirebaseRepository
import com.example.mapviewpoint.usecase.CurrentLocationUseCase
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class MapViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
    private val gpsFirebaseRepository: GpsFirebaseRepository,
    private val currentLocationUseCase: CurrentLocationUseCase
) : ViewModel() {

    private val twentyFourHoursCoordinates = MutableLiveData<List<GpsCoordinates>>()
    fun getTwentyFourHoursCoordinates(): LiveData<List<GpsCoordinates>> {
        return twentyFourHoursCoordinates
    }

    private val chosenDateCoordinates = MutableLiveData<List<GpsCoordinates>>()
    fun getChosenDateCoordinates(): LiveData<List<GpsCoordinates>> {
        return chosenDateCoordinates
    }

    private val currentCoordinates = MutableLiveData<List<LatLng>>()
    fun getCurrentCoordinates(): LiveData<List<LatLng>> {
        return currentCoordinates
    }

    fun getUserId(): String {
        return userPreferences.getUserId().toString()
    }

    fun getCurrentLocation()  {
        viewModelScope.launch {
            val location = currentLocationUseCase.getCurrentLocation()
            val currentList = currentCoordinates.value?.toMutableList() ?: mutableListOf()
            currentList.add(location)
            currentCoordinates.value = currentList.toList()
        }
    }

    suspend fun getGpsCoordinatesByTime(time: Long)  {
        viewModelScope.launch {
            val coordinatesList: List<GpsCoordinates> = gpsFirebaseRepository.getCoordinatesByTime(time)
            chosenDateCoordinates.value = coordinatesList
        }
    }

    fun getGpsCoordinatesByLast24Hours() {
        viewModelScope.launch {
            val coordinatesList: List<GpsCoordinates> = gpsFirebaseRepository.getCoordinatesLast24Hours()
            twentyFourHoursCoordinates.value = coordinatesList
        }
    }
}