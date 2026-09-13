package com.example.s8161372assignment2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.s8161372assignment2.model.DashboardResponse
import com.example.s8161372assignment2.repository.DashboardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: DashboardRepository
) : ViewModel() {

    private val _dashboardData = MutableLiveData<DashboardResponse?>()
    val dashboardData: LiveData<DashboardResponse?> = _dashboardData

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun loadDashboard(keypass: String) {

        if (keypass.isBlank()) {
            _errorMessage.value = "Invalid keypass"
            return
        }

        viewModelScope.launch {

            _isLoading.value = true

            try {
                val response = repository.getDashboard(keypass)

                if (response.isSuccessful) {

                    val body = response.body()

                    if (body != null) {
                        _dashboardData.value = body
                    } else {
                        _errorMessage.value = "Dashboard response was empty"
                    }

                } else {

                    val serverMessage = response.errorBody()?.string()

                    _errorMessage.value =
                        if (!serverMessage.isNullOrBlank()) {
                            "Error ${response.code()}: $serverMessage"
                        } else {
                            "Dashboard failed: ${response.code()}"
                        }
                }

            } catch (e: Exception) {

                _errorMessage.value =
                    "Connection error: ${e.message ?: "Unknown error"}"

            } finally {

                _isLoading.value = false
            }
        }
    }
}