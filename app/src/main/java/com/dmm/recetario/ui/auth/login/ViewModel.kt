package com.dmm.recetario.ui.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.recetario.data.local.TokenManager
import com.dmm.recetario.data.local.database.dao.UserDAO
import com.dmm.recetario.data.local.database.entity.TokenUserRef
import com.dmm.recetario.data.service.AuthService
import com.dmm.recetario.domain.repository.LoginData
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor (
    private val service: AuthService,
    private val tokenManager: TokenManager,
    private val dao: UserDAO
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

                saveTokenToPreferences(token)
                saveTokenToDatabase(token, email)
                uiState = LoginUiState.Success(token)
            } catch (e: Exception) {
                uiState = LoginUiState.Error("Error: ${e.message}")
            }
        }
    }

    private suspend fun saveTokenToPreferences(token: String) {
        tokenManager.saveToken(token)
    }

    private fun saveTokenToDatabase(token: String, email: String) {
        dao.insertReferences(listOf(TokenUserRef(token, email)))
    }

    fun loginAsGuest() {
        viewModelScope.launch {
            val token = ""
            saveTokenToPreferences(token)
            uiState = LoginUiState.Success(token)
        }
    }

    fun resetToIdle() {
        uiState = LoginUiState.Idle
    }
}