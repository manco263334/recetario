package com.dmm.recetario.ui.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.recetario.data.local.TokenManager
import com.dmm.recetario.data.local.UserManager
import com.dmm.recetario.data.service.AuthService
import com.dmm.recetario.data.service.CategoryService
import com.dmm.recetario.domain.model.AnonymousUser
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor (
    private val tokenManager: TokenManager,
    private val userManager: UserManager,
    private val authService: AuthService,
    private val categoryService: CategoryService
): ViewModel() {
    var uiState by mutableStateOf<HomeUiState>(HomeUiState.Loading)
        private set

    init {
        loadHomeData()
    }

    fun loadHomeData() {
        viewModelScope.launch {
            uiState = HomeUiState.Loading

            try {
                val categories = categoryService.getAllCategories()
                val user = userManager.getUser() ?: AnonymousUser()
                uiState = HomeUiState.Success (
                    user = user,
                    categories = categories,
                    showFAB = user !is AnonymousUser
                )
            } catch (e: Exception) {
                uiState = HomeUiState.Error("No se pudieron cargar las recetas, mano")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authService.logout()
            tokenManager.clearToken()
            uiState = HomeUiState.LoggedOutSuccess
        }
    }
}