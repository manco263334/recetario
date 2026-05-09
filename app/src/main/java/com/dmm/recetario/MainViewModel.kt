package com.dmm.recetario

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.recetario.data.local.TokenManager
import com.dmm.recetario.navigation.Home
import com.dmm.recetario.navigation.Login
import com.dmm.recetario.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor (
    private val tokenManager: TokenManager
): ViewModel() {
    var startDestination by mutableStateOf<Routes?>(null)
        private set

    init {
        checkAuthStatus()
    }

    private fun checkAuthStatus() {
        viewModelScope.launch {
            val token = tokenManager.token.firstOrNull()
            startDestination = if (token != null) Routes.Home else Routes.Login
        }
    }
}

//@HiltViewModel
//class MainViewModel @Inject constructor (
//    private val tokenManager: TokenManager
//): ViewModel() {
//    var startDestination by mutableStateOf<Any?>(null)
//        private set
//
//    init {
//        checkAuthStatus()
//    }
//
//    private fun checkAuthStatus() {
//        viewModelScope.launch {
//            val token = tokenManager.token.firstOrNull()
//            startDestination = if (token != null) Home else Login
//        }
//    }
//}