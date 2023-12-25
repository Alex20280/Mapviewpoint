package com.example.mapviewpoint.usecase

import com.example.mapviewpoint.network.ErrorDto
import com.example.mapviewpoint.network.RequestResult
import com.example.mapviewpoint.repository.FakeAuthenticationRepository
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.AuthResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

class UserLoginUseCaseTest {

    private lateinit var mockRepo: FakeAuthenticationRepository

    @Before
    fun setUp(){
        mockRepo = mockk<FakeAuthenticationRepository>()
    }

    @Test
    fun `should return login success result`() = runBlocking {

        // Stub coordinates
        val expectedAuthResult = mockk<AuthResult>() // Mocking an AuthResult for testing purposes
        val expectedLoginResult = RequestResult.Success(expectedAuthResult)

        val emailAddress = "test@gmail.com"
        val password = "sacscsdcds"

        coEvery { mockRepo.loginUser(emailAddress, password) } returns expectedLoginResult

        // Use case with mock repository
        val useCase = UserLoginUseCase(mockRepo)

        // Execute
        val result = useCase.loginUser(emailAddress, password)

        // Verify and assert
        coVerify(exactly = 1) {
            mockRepo.loginUser(emailAddress, password)
        }
        assertEquals(expectedLoginResult, result)
    }

    @Test
    fun `should return login fail result`() = runBlocking {

        // Stub coordinates
        val expectedLoginResult = RequestResult.Error(ErrorDto.Default("Login problem"), 0)

        val emailAddress = "test@gmail.com"
        val password = "sacscsdcds"

        coEvery { mockRepo.loginUser(emailAddress, password) } returns expectedLoginResult

        // Use case with mock repository
        val useCase = UserLoginUseCase(mockRepo)

        // Execute
        val result = useCase.loginUser(emailAddress, password)

        // Verify and assert
        coVerify(exactly = 1) {
            mockRepo.loginUser(emailAddress, password)
        }
        assertEquals(expectedLoginResult, result)
    }
}