package com.example.s8161372assignment2

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.s8161372assignment2.model.LoginResponse
import com.example.s8161372assignment2.repository.LoginRepository
import com.example.s8161372assignment2.viewmodel.LoginViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var repository: LoginRepository
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        repository = mockk()
        viewModel = LoginViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun emptyLoginShowsError() {

        viewModel.login("", "")

        assertEquals(
            "Please enter Student ID and first name",
            viewModel.errorMessage.value
        )
    }

    @Test
    fun successfulLoginReturnsKeypass() = runTest {

        val loginResponse = LoginResponse(
            keypass = "test-keypass"
        )

        coEvery {
            repository.login("8161372", "Md Prantor")
        } returns Response.success(loginResponse)

        viewModel.login(
            "8161372",
            "Md Prantor"
        )

        advanceUntilIdle()

        assertEquals(
            "test-keypass",
            viewModel.loginResult.value?.keypass
        )

        assertEquals(
            false,
            viewModel.isLoading.value
        )
    }

    @Test
    fun failedLoginShowsError() = runTest {

        val errorBody = ResponseBody.create(
            null,
            "Invalid login"
        )

        coEvery {
            repository.login(
                "8161372",
                "WrongName"
            )
        } returns Response.error(
            400,
            errorBody
        )

        viewModel.login(
            "8161372",
            "WrongName"
        )

        advanceUntilIdle()

        assertNotNull(
            viewModel.errorMessage.value
        )
    }
}