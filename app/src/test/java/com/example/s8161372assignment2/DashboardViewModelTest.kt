package com.example.s8161372assignment2

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.s8161372assignment2.model.DashboardResponse
import com.example.s8161372assignment2.repository.DashboardRepository
import com.example.s8161372assignment2.viewmodel.DashboardViewModel
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
class DashboardViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var repository: DashboardRepository
    private lateinit var viewModel: DashboardViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        repository = mockk()
        viewModel = DashboardViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun emptyKeypassShowsError() {

        viewModel.loadDashboard("")

        assertEquals(
            "Invalid keypass",
            viewModel.errorMessage.value
        )
    }

    @Test
    fun successfulDashboardLoadReturnsData() = runTest {

        val dashboardResponse = DashboardResponse(
            entities = emptyList(),
            entityTotal = 0
        )

        coEvery {
            repository.getDashboard("test-keypass")
        } returns Response.success(dashboardResponse)

        viewModel.loadDashboard("test-keypass")

        advanceUntilIdle()

        assertEquals(
            dashboardResponse,
            viewModel.dashboardData.value
        )

        assertEquals(
            false,
            viewModel.isLoading.value
        )
    }

    @Test
    fun failedDashboardLoadShowsError() = runTest {

        val errorBody = ResponseBody.create(
            null,
            "Invalid dashboard request"
        )

        coEvery {
            repository.getDashboard("bad-keypass")
        } returns Response.error(
            400,
            errorBody
        )

        viewModel.loadDashboard("bad-keypass")

        advanceUntilIdle()

        assertNotNull(
            viewModel.errorMessage.value
        )

        assertEquals(
            false,
            viewModel.isLoading.value
        )
    }
}