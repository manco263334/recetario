package com.dmm.recetario.data.local

import android.util.Log
import com.dmm.recetario.core.jwt.isTokenExpired
import com.dmm.recetario.core.utils.mapper.toDomain
import com.dmm.recetario.core.utils.mapper.toEntity
import com.dmm.recetario.data.local.database.dao.UserDAO
import com.dmm.recetario.data.repository.UserRepository
import com.dmm.recetario.data.service.AuthService
import com.dmm.recetario.domain.model.User
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.firstOrNull

@Singleton
class UserManager @Inject constructor (
    private val tokenManager: TokenManager,
    private val authService: AuthService,
    private val userRepository: UserRepository,
    private val userDao: UserDAO
) {
    suspend fun getUserByAPI(): User {
        val me = authService.me()
        val user = userRepository.getUser(me.id)

        return user
    }

    suspend fun syncUser() {
        val token = tokenManager.token.firstOrNull()?.ifBlank { null }

        if (token == null) return

        if (isTokenExpired(token)) return

        val userFromDao = userDao.getUserByToken(token).firstOrNull()?.toDomain()

        if (userFromDao != null) return

        try {
            val userFromAPI = getUserByAPI()

            userDao.saveUsers(listOf(userFromAPI.toEntity()))
        } catch (e: Exception) {
            Log.e("UserManager", "Error syncing user: ${e.message}", e)
        }
    }
}