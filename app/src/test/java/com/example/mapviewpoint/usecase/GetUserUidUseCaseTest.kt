package com.example.mapviewpoint.usecase

import com.example.mapviewpoint.model.GpsCoordinates
import com.example.mapviewpoint.repository.FakeAuthenticationRepository
import com.example.mapviewpoint.repository.FakeGpsCoordinatesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

class GetUserUidUseCaseTest {

    private lateinit var mockRepo: FakeAuthenticationRepository

    @Before
    fun setUp(){
        mockRepo = mockk<FakeAuthenticationRepository>()
    }

    @Test
    fun `should receive client uid`() = runBlocking {

        // Stub coordinates
        val uid = "hcbdshjcvbsdlcjdnskjcld"

        coEvery { mockRepo.getUserUd() } returns uid

        // Use case with mock repository
        val useCase = GetUserUidUseCase(mockRepo)

        // Execute
        val result = useCase.getUserUid()

        // Verify and assert
        coVerify(exactly = 1) {
            mockRepo.getUserUd()
        }
        assertEquals(result,uid)
    }
}