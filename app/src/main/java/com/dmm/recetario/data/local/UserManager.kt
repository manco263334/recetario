package com.dmm.recetario.data.local

import android.util.Log
import com.dmm.recetario.core.jwt.isTokenExpired
import com.dmm.recetario.domain.exceptions.APIException
import com.dmm.recetario.core.utils.mapper.toEntity
import com.dmm.recetario.data.local.database.dao.UserDao
import com.dmm.recetario.domain.model.User
import com.dmm.recetario.domain.repository.UserRepository
import com.dmm.recetario.domain.service.AuthService
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.firstOrNull

@Singleton
class UserManager @Inject constructor (
    private val tokenManager: TokenManager,
    private val authService: AuthService,
    private val userRepository: UserRepository,
    private val userDao: UserDao
) {
    suspend fun getUserByAPI(): User {
        val me = authService.me()
        val user = userRepository.getUser(me.id, false)

        return user
    }

    suspend fun syncUser() {
        val token = tokenManager.token.firstOrNull()?.ifBlank { null }

        if (token == null) return

        if (isTokenExpired(token)) return

        try {
            val userFromAPI = getUserByAPI()

            userDao.saveUser(userFromAPI.toEntity())
        } catch (e: APIException) {
            Log.e("UserManager", "Error syncing user: ${e.message}", e)
        }
    }
}