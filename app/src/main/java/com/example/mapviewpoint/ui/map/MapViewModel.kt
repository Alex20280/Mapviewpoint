package com.example.mapviewpoint.ui.map

import android.app.Application
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.provider.Settings
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mapviewpoint.model.GpsCoordinates
import com.example.mapviewpoint.usecase.GetCurrentLocationUseCase
import com.example.mapviewpoint.usecase.GetDailyLocationCoordinateUseCase
import com.example.mapviewpoint.usecase.GetLocationCoordinatesInTimeRangeUseCase
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch
import javax.inject.Inject

class MapViewModel @Inject constructor(
    private val application: Application,
    private val receiveCoordinatesUseCase: GetLocationCoordinatesInTimeRangeUseCase,
    private val getDailyLocationCoordinateUseCase: GetDailyLocationCoordinateUseCase,
    private val currentLocationUseCase: GetCurrentLocationUseCase
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

    fun isLocationEnabled(): Boolean {
        val locationManager =
            application.applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    fun requestLocationEnable() {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        application.applicationContext.startActivity(intent)
    }

    fun getCurrentLocation()  {
        viewModelScope.launch {
            val location = currentLocationUseCase.getCurrentLocation()
            val currentList = currentCoordinates.value?.toMutableList() ?: mutableListOf()
            currentList.add(location)
            currentCoordinates.postValue(currentList.toList())
        }
    }

    suspend fun getGpsCoordinatesByTime(time: Long)  {
        viewModelScope.launch {
            val coordinatesList: List<GpsCoordinates> = receiveCoordinatesUseCase.getCoordinatesInTimeRange(time)
            chosenDateCoordinates.postValue(coordinatesList)
        }
    }

    fun getGpsCoordinatesByLast24Hours() {
        viewModelScope.launch {
            val coordinatesList: List<GpsCoordinates> = getDailyLocationCoordinateUseCase.getCoordinatesWithin24Hours()
            twentyFourHoursCoordinates.postValue(coordinatesList)
        }
    }
}