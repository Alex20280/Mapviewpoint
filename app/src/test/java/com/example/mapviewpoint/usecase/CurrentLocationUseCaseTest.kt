package com.example.mapviewpoint.usecase

import com.example.mapviewpoint.repository.FakeLocationTrackerRepository
import com.google.android.gms.maps.model.LatLng
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

class CurrentLocationUseCaseTest {

    private lateinit var mockRepo: FakeLocationTrackerRepository

    @Before
    fun setUp(){
        mockRepo = mockk<FakeLocationTrackerRepository>()
    }

    @Test
    fun `should receive current location`() = runBlocking {

        // Stub coordinates
        val expectedCoordinates  = LatLng(49.96048115488885, 36.2232729065569)
        coEvery { mockRepo.getCurrentLocation() } returns expectedCoordinates

        // Use case with mock repository
        val useCase = GetCurrentLocationUseCase(mockRepo)

        // Execute
        val result = useCase.getCurrentLocation()

        // Verify and assert
        coVerify(exactly = 1) {
            mockRepo.getCurrentLocation()
        }
        assertEquals(expectedCoordinates, result)
    }
}