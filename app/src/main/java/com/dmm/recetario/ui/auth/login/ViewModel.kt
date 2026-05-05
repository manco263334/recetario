package com.dmm.recetario.ui.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.recetario.di.RetrofitClient
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    var uiState by mutableStateOf<LoginUiState>(LoginUiState.Idle)
    private set

    fun login(username: String, password: String) {
        viewModelScope.launch {
            uiState = LoginUiState.Loading

            try {
                val response = RetrofitClient.instance
            } catch (e: Exception) {
                uiState = LoginUiState.Error("Error de red: ${e.message}")
            }
        }
    }
}