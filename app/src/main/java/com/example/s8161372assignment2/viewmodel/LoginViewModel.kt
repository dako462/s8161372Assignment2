package com.example.s8161372assignment2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.s8161372assignment2.model.LoginResponse
import com.example.s8161372assignment2.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository
) : ViewModel() {

    private val _loginResult = MutableLiveData<LoginResponse?>()
    val loginResult: LiveData<LoginResponse?> = _loginResult

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun login(username: String, password: String) {

        if (username.isBlank() || password.isBlank()) {
            _errorMessage.value = "Please enter Student ID and first name"
            return
        }

        viewModelScope.launch {

            _isLoading.value = true

            try {
                val response = repository.login(username, password)

                if (response.isSuccessful) {

                    val body = response.body()

                    if (body != null) {
                        _loginResult.value = body
                    } else {
                        _errorMessage.value = "Login response was empty"
                    }

                } else {

                    val serverMessage = response.errorBody()?.string()

                    _errorMessage.value =
                        if (!serverMessage.isNullOrBlank()) {
                            "Error ${response.code()}: $serverMessage"
                        } else {
                            "Login failed: ${response.code()}"
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