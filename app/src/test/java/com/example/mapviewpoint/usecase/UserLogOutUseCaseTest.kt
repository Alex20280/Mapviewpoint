package com.example.mapviewpoint.usecase

import com.example.mapviewpoint.network.ErrorDto
import com.example.mapviewpoint.network.RequestResult
import com.example.mapviewpoint.repository.FakeAuthenticationRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

class UserLogOutUseCaseTest {

    private lateinit var mockRepo: FakeAuthenticationRepository

    @Before
    fun setUp(){
        mockRepo = mockk<FakeAuthenticationRepository>()
    }

    @Test
    fun `should return log out success result`() = runBlocking {

        // Stub coordinates
        val expectedLoginResult = RequestResult.Success(Unit)

        coEvery { mockRepo.logout() } returns expectedLoginResult

        // Use case with mock repository
        val useCase = UserLogOutUseCase(mockRepo)

        // Execute
        val result = useCase.userLogout()

        // Verify and assert
        coVerify(exactly = 1) {
            mockRepo.logout()
        }
        assertEquals(expectedLoginResult, result)
    }

    @Test
    fun `should return log out fail result`() = runBlocking {

        // Stub coordinates
        val expectedLoginResult = RequestResult.Error(ErrorDto.Default("Logout problem"), 0)

        coEvery { mockRepo.logout() } returns expectedLoginResult

        // Use case with mock repository
        val useCase = UserLogOutUseCase(mockRepo)

        // Execute
        val result = useCase.userLogout()

        // Verify and assert
        coVerify(exactly = 1) {
            mockRepo.logout()
        }
        assertEquals(expectedLoginResult, result)
    }
}