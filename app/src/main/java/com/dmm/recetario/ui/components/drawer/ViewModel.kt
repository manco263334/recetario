package com.dmm.recetario.ui.components.drawer

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.recetario.data.local.TokenManager
import com.dmm.recetario.data.local.database.dao.UserDAO
import com.dmm.recetario.data.service.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class DrawerViewModel @Inject constructor (
    private val tokenManager: TokenManager,
    private val authService: AuthService,
    private val userDAO: UserDAO
): ViewModel() {
    var logOutSuccessful by mutableStateOf<Boolean?>(null)
        private set

    fun logout() {
        viewModelScope.launch {
            try {
                authService.logout()
                tokenManager.clearToken()
                userDAO.clear()
                userDAO.clearTokenRefs()

                logOutSuccessful = true
            } catch (e: Exception) {
                Log.w("DrawerViewModel", "Error al cerrar sesión: ${e.message}")
                logOutSuccessful = false
            }
        }
    }
}