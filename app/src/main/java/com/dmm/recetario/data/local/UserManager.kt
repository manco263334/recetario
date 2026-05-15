package com.dmm.recetario.data.local

import com.dmm.recetario.data.service.AuthService
import com.dmm.recetario.data.service.UserService
import com.dmm.recetario.domain.model.User
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.firstOrNull

@Singleton
class UserManager @Inject constructor (
    private val tokenManager: TokenManager,
    private val authService: AuthService,
    private val userService: UserService
) {
    suspend fun getUser(): User? {
        val token = tokenManager.token.firstOrNull()?.ifBlank { null }

        return token?.let {
            val data = authService.me()
            val id = data.id
            userService.getUser(id)
        }
    }
}