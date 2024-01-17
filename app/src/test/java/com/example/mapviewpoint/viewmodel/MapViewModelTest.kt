package com.example.mapviewpoint.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.mapviewpoint.ui.map.MapViewModel
import com.example.mapviewpoint.usecase.GetCurrentLocationUseCase
import com.example.mapviewpoint.usecase.GetDailyLocationCoordinateUseCase
import com.example.mapviewpoint.usecase.GetLocationCoordinatesInTimeRangeUseCase
import com.google.android.gms.maps.model.LatLng
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MapViewModelTest {

    private lateinit var viewModel: MapViewModel
    private lateinit var receiveCoordinatesUseCase: GetLocationCoordinatesInTimeRangeUseCase
    private lateinit var getDailyLocationCoordinateUseCase: GetDailyLocationCoordinateUseCase
    private lateinit var currentLocationUseCase: GetCurrentLocationUseCase

    private val expectedLocation = LatLng(10.0, 20.0)

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule() // for liveData

    @OptIn(ExperimentalCoroutinesApi::class)
    private val dispatcher = StandardTestDispatcher()  // for coroutine

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {

        Dispatchers.setMain(dispatcher)

        currentLocationUseCase = mockk()
        coEvery { currentLocationUseCase.getCurrentLocation() } returns expectedLocation
        receiveCoordinatesUseCase = mockk()
        getDailyLocationCoordinateUseCase = mockk()

        viewModel = MapViewModel(
            receiveCoordinatesUseCase,
            getDailyLocationCoordinateUseCase,
            currentLocationUseCase
        )
        /*        receiveCoordinatesUseCase = mockk()
                getDailyLocationCoordinateUseCase = mockk()
                currentLocationUseCase = mockk{
                    coEvery { currentLocationUseCase.getCurrentLocation() } returns expectedLocation
                }

                viewModel = MapViewModel(
                    receiveCoordinatesUseCase,
                    getDailyLocationCoordinateUseCase,
                    currentLocationUseCase
                )*/
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getCurrentLocationTest() {


        viewModel.getCurrentLocation()
        dispatcher.scheduler.advanceUntilIdle()


        // Simulate a delay to allow the LiveData to be updated (adjust this based on your app's logic)
        //delay(1000) // Wait for 1 second (or use any relevant delay)

        // Perform assertions here based on the observed LiveData value
        val updatedCoordinates = viewModel.getCurrentCoordinates().value
        assertNotNull(updatedCoordinates)
        assertTrue(updatedCoordinates!!.contains(expectedLocation))
    }

    /*    @Test
        fun getCurrentLocationTest() = runBlocking {
            // Given
            val expectedLocation = LatLng(10.0, 20.0)
            coEvery { currentLocationUseCase.getCurrentLocation() } returns expectedLocation

            // When
            viewModel.getCurrentLocation()

            // Then
            val currentCoordinates = viewModel.getCurrentCoordinates().value
            assertEquals(1, currentCoordinates?.size)
            assertEquals(expectedLocation, currentCoordinates?.get(0))
        }*/


}