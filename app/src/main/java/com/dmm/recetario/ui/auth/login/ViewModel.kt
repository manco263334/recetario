package com.dmm.recetario.ui.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.recetario.data.local.TokenManager
import com.dmm.recetario.data.service.AuthService
import com.dmm.recetario.domain.repository.LoginData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor (
    private val service: AuthService,
    private val tokenManager: TokenManager
): ViewModel() {
    var uiState by mutableStateOf<LoginUiState>(LoginUiState.Idle)
        private set

    fun login (
        email: String,
        password: String
    ) {
        uiState = LoginUiState.Loading

        viewModelScope.launch {
            try {
                val data = LoginData(email, password)
                val response = service.login(data)
                val token = response.token

                tokenManager.saveToken(token)
                uiState = LoginUiState.Success(token)
            } catch (e: Exception) {
                uiState = LoginUiState.Error("Error: ${e.message}")
            }
        }
    }

    fun loginAsGuest () {
        viewModelScope.launch {
            val token = ""
            tokenManager.saveToken(token)
            uiState = LoginUiState.Success(token)
        }
    }

    fun resetToIdle () {
        uiState = LoginUiState.Idle
    }
}