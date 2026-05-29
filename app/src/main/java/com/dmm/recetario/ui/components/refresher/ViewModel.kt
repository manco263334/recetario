package com.dmm.recetario.ui.components.refresher

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class RefresherViewModel @Inject constructor(): ViewModel() {
    var uiState by mutableStateOf<RefresherUiState>(RefresherUiState.Idle)
        private set

    fun refresh(call: suspend () -> Unit) {
        if (uiState is RefresherUiState.Loading) return

        viewModelScope.launch {
            uiState = RefresherUiState.Loading

            uiState = try {
                call()
                RefresherUiState.Success("Elements refreshed successfully")
            } catch (e: Exception) {
                RefresherUiState.Error("Error while refreshing elements: ${e.message}")
            }
        }
    }
}