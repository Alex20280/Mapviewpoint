package com.example.mapviewpoint.usecase

import com.example.mapviewpoint.model.GpsCoordinates
import com.example.mapviewpoint.repository.FakeGpsCoordinatesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class GetLocationCoordinatesInTimeRangeUseCaseTest {

    private lateinit var mockRepo: FakeGpsCoordinatesRepository
    private var timeRange: Long = 0L

    @Before
    fun setUp(){
        mockRepo = mockk<FakeGpsCoordinatesRepository>()
        timeRange = 1L
    }

    @Test
    fun `should receive coordinates for TimeRange`() = runBlocking {

        // Stub coordinates
        val coordinates = listOf(
            GpsCoordinates(1.1, 1.1, 1),
            GpsCoordinates(2.2, 2.2, 2)
        )
        coEvery { mockRepo.getCoordinatesByTime(timeRange) } returns coordinates

        // Use case with mock repository
        val useCase = GetLocationCoordinatesInTimeRangeUseCase(mockRepo)

        // Execute
        val result = useCase.getCoordinatesInTimeRange(timeRange)

        // Verify and assert
        coVerify(exactly = 1) {
            mockRepo.getCoordinatesByTime(timeRange)
        }
        assertTrue(result.isNotEmpty())
    }

    @Test
    fun `should not receive coordinates for TimeRange`() = runBlocking {

        val coordinates = mutableListOf<GpsCoordinates>()

        coEvery { mockRepo.getCoordinatesByTime(timeRange) } returns coordinates

        val result = mockRepo.getCoordinatesByTime(timeRange)

        assertTrue(result.isEmpty())
    }

}