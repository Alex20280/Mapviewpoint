package com.example.mapviewpoint.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mapviewpoint.model.GpsCoordinates
import com.example.mapviewpoint.prefs.UserPreferences
import com.example.mapviewpoint.repository.GpsFirebaseRepositoryImpl
import com.example.mapviewpoint.usecase.CurrentLocationUseCase
import com.example.mapviewpoint.usecase.ReceiveCoordinatesUseCase
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch
import javax.inject.Inject

class MapViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
    private val receiveCoordinatesUseCase: ReceiveCoordinatesUseCase,
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
            val coordinatesList: List<GpsCoordinates> = receiveCoordinatesUseCase.getCoordinatesByTime(time)
            chosenDateCoordinates.value = coordinatesList
        }
    }

    fun getGpsCoordinatesByLast24Hours() {
        viewModelScope.launch {
            val coordinatesList: List<GpsCoordinates> = receiveCoordinatesUseCase.getCoordinatesLast24Hours()
            twentyFourHoursCoordinates.value = coordinatesList
        }
    }
}