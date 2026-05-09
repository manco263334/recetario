package com.dmm.recetario.ui.home

import com.dmm.recetario.domain.model.Category
import com.dmm.recetario.domain.model.User

sealed interface HomeUiState {
    object Loading: HomeUiState

    data class Success (
        val user: User?,
        val categories: List<Category>,
        val showFAB: Boolean
    ): HomeUiState

    object LoggedOutSuccess: HomeUiState

    data class Error(val message: String): HomeUiState
}