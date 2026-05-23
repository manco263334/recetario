package com.dmm.recetario.data.local

import com.dmm.recetario.core.utils.isTokenExpired
import com.dmm.recetario.core.utils.mapper.toDomain
import com.dmm.recetario.data.local.database.dao.UserDAO
import com.dmm.recetario.domain.model.User
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.firstOrNull

@Singleton
class UserManager @Inject constructor (
    private val tokenManager: TokenManager,
    private val userDao: UserDAO
) {
    suspend fun getUser(): User? {
        val token = tokenManager.token.firstOrNull()?.ifBlank { null }

        if (token == null) return null

        if (isTokenExpired(token)) {
            userDao.deleteReference(token)
            return null
        }

        return userDao.getUserByToken(token).firstOrNull()?.toDomain()
    }
}