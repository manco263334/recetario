package com.dmm.recetario.data.service

import com.dmm.recetario.core.utils.handler.handleApiCall
import com.dmm.recetario.data.local.database.dao.UserDAO
import com.dmm.recetario.data.remote.retrofit.APIAuthService
import com.dmm.recetario.domain.repository.LoginData
import com.dmm.recetario.domain.repository.LoginResponse
import com.dmm.recetario.domain.repository.MeResponse
import com.dmm.recetario.domain.repository.RegisterData
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthService @Inject constructor (
    private val repository: APIAuthService
) {
    suspend fun login(data: LoginData): LoginResponse {
        return withContext(Dispatchers.IO) {
            val response = handleApiCall { repository.login(data) }
            response ?: throw Exception("Credenciales Inválidas")
        }
    }

    suspend fun register(data: RegisterData): LoginResponse {
        return withContext(Dispatchers.IO) {
            val response = handleApiCall { repository.register(data) }
            response!!
        }
    }

    suspend fun logout() {
        return withContext(Dispatchers.IO) {
            handleApiCall { repository.logout() }
        }
    }

    suspend fun me(): MeResponse {
        return withContext(Dispatchers.IO) {
            val response = handleApiCall { repository.me() }
            response!!
        }
    }
}