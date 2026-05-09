package com.dmm.recetario.data.local

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.dmm.recetario.data.service.AuthService
import com.dmm.recetario.data.service.UserService
import com.dmm.recetario.domain.model.User
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject
import javax.inject.Singleton

data class UserSaved (
    val name: String,
    val username: String?
)

val Context.dataStore by preferencesDataStore(name = "user_preferences")

@Singleton
class UserManager @Inject constructor (
    private val tokenManager: TokenManager,
    private val authService: AuthService,
    private val userService: UserService
) {
    suspend fun getUserFromToken(): UserSaved? {
        val token = tokenManager.token.firstOrNull()?.ifBlank { null }

        return token?.let {
            val data = authService.me()
            UserSaved (
                name = data.name,
                username = data.username
            )
        }
    }

    suspend fun getUser(): User? {
        val token = tokenManager.token.firstOrNull()?.ifBlank { null }

        return token?.let {
            val data = authService.me()
            val id = data.id
            userService.getUser(id)
        }
    }
}