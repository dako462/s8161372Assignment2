package com.example.s8161372assignment2.repository

import com.example.s8161372assignment2.api.ApiService
import com.example.s8161372assignment2.model.DashboardResponse
import retrofit2.Response
import javax.inject.Inject

class DashboardRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getDashboard(
        keypass: String
    ): Response<DashboardResponse> {
        return apiService.getDashboard(keypass)
    }
}