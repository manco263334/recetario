package com.dmm.recetario.ui.auth.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.recetario.data.local.TokenManager
import com.dmm.recetario.data.service.AuthService
import com.dmm.recetario.domain.repository.RegisterData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor (
    private val service: AuthService,
    private val tokenManager: TokenManager
): ViewModel() {
    var uiState by mutableStateOf<RegisterUiState>(RegisterUiState.Idle)
        private set

    fun register (
        name: String,
        email: String,
        password: String,
        phone: String?,
        username: String?
    ) {
        uiState = RegisterUiState.Loading

        viewModelScope.launch {
            try {
                val data = RegisterData(name, email, password, phone, username)
                val response = service.register(data)
                val token = response.token

                tokenManager.saveToken(token)
                uiState = RegisterUiState.Success(token)
            } catch (e: Exception) {
                uiState = RegisterUiState.Error("Error: ${e.message}")
            }
        }
    }

    fun resetToIdle () {
        uiState = RegisterUiState.Idle
    }
}