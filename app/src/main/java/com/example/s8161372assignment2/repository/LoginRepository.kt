package com.example.s8161372assignment2.repository

import com.example.s8161372assignment2.api.ApiService
import com.example.s8161372assignment2.model.LoginRequest
import com.example.s8161372assignment2.model.LoginResponse
import retrofit2.Response
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun login(
        username: String,
        password: String
    ): Response<LoginResponse> {
        return apiService.login(
            LoginRequest(
                username = username,
                password = password
            )
        )
    }
}