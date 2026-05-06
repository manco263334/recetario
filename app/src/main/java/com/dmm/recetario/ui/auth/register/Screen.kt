package com.dmm.recetario.ui.auth.register

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun RegisterScreen (
    viewModel: RegisterViewModel = hiltViewModel(),
    onNavigateToHome: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    RegisterContent (
        uiState = viewModel.uiState,
        onRegister = viewModel::register,
        onRetry = viewModel::resetToIdle,
        onNavigateToHome = onNavigateToHome,
        onNavigateToLogin = onNavigateToLogin
    )
}

@Composable
fun RegisterContent (
    uiState: RegisterUiState,
    onRegister: (String, String, String, String?, String?) -> Unit,
    onRetry: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    LaunchedEffect(uiState) {
        if (uiState is RegisterUiState.Success) {
            onNavigateToHome()
        }
    }

    when (uiState) {
        is RegisterUiState.Loading -> CircularProgressIndicator()
        is RegisterUiState.Error -> {
            RegisterError (message = uiState.message, onRetry = onRetry)
        }
        else -> {
            RegisterForm (onRegister = onRegister)
        }
    }
}

@Composable
private fun RegisterError (
    message: String,
    onRetry: () -> Unit
) {
    TODO("Not yet implemented")
}

@Composable
private fun RegisterForm (
    onRegister: (String, String, String, String?, String?) -> Unit
) {
    TODO("Not yet implemented")
}