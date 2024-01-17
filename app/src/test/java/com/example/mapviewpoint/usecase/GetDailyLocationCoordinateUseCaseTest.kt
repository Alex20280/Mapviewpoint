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

class GetDailyLocationCoordinateUseCaseTest {

    private lateinit var mockRepo: FakeGpsCoordinatesRepository

    @Before
    fun setUp(){
         mockRepo = mockk<FakeGpsCoordinatesRepository>()
    }

    @Test
    fun `should receive coordinates for last 24 hours`() = runBlocking {

        // Stub coordinates
        val coordinates = listOf(
            GpsCoordinates(1.1, 1.1, 1),
            GpsCoordinates(2.2, 2.2, 2)
        )
        coEvery { mockRepo.getCoordinatesLast24Hours() } returns coordinates

        // Use case with mock repository
        val useCase = GetDailyLocationCoordinateUseCase(mockRepo)

        // Execute
        val result = useCase.getCoordinatesWithin24Hours()

        // Verify and assert
        coVerify(exactly = 1) {
            mockRepo.getCoordinatesLast24Hours()
        }
        assertTrue(result.isNotEmpty())
    }

    @Test
    fun `should not receive coordinates for last 24 hours`() = runBlocking {

        val coordinates = mutableListOf<GpsCoordinates>()

        coEvery { mockRepo.getCoordinatesLast24Hours() } returns coordinates

        val result = mockRepo.getCoordinatesLast24Hours()

        assertTrue(result.isEmpty())
    }

}