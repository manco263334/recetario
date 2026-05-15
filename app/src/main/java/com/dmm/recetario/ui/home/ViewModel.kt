package com.dmm.recetario.ui.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.recetario.data.service.CategoryService
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor (
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
                uiState = HomeUiState.Success (
                    categories = categories
                )
            } catch (e: Exception) {
                Log.w("HomeViewModel", "Error loading home data", e)
                uiState = HomeUiState.Error("No se pudieron cargar las recetas, mano")
            }
        }
    }
}