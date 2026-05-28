package com.dmm.recetario.data.service

import android.util.Log
import com.dmm.recetario.core.utils.extension.isNotNull
import com.dmm.recetario.core.utils.handler.APIException
import com.dmm.recetario.core.utils.mapper.toDomain
import com.dmm.recetario.core.utils.mapper.toEntity
import com.dmm.recetario.data.local.database.dao.UserDAO
import com.dmm.recetario.data.repository.UserRepository
import com.dmm.recetario.domain.model.AnonymousUser
import com.dmm.recetario.domain.model.User
import com.dmm.recetario.domain.use_cases.user.DeleteUserUseCase
import com.dmm.recetario.domain.use_cases.user.UpdateUserUseCase
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class UserService @Inject constructor (
    private val updateUserUseCase: UpdateUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val dao: UserDAO,
    private val userRepository: UserRepository
) {
    fun getAllUsers(): Flow<List<User>> {
        return dao.getUsers().map { users ->
            users.map { user ->
                user.toDomain()
            }
        }
    }

    suspend fun syncUsers (
        page: Int = 0,
        size: Int = 10,
        withRecipes: Boolean? = null
    ) {
        try {
            val users = userRepository.getAllUsers (
                page = page,
                size = size,
                withRecipes = withRecipes
            )
            dao.saveUsers(users.map { it.toEntity() })
        } catch (e: APIException) {
            Log.e("UserService", "Error syncing users: ${e.message}", e)
        }
    }

    fun getUserById(id: String): Flow<User?> {
        return dao.getUser(id).map { user ->
            user?.toDomain()
        }
    }

    fun getUserByEmail(email: String): Flow<User?> {
        return dao.getUserByEmail(email).map { user ->
            user?.toDomain()
        }
    }

    fun getUserByUsername(username: String): Flow<User?> {
        return dao.getUserByUsername(username).map { user ->
            user?.toDomain()
        }
    }

    fun getUserByTokenOrAnonymous(token: String): Flow<User?> {
        return dao.getUserByToken(token).map { user ->
            user?.toDomain() ?: AnonymousUser()
        }
    }

    suspend fun updateUser (
        id: String,
        data: User
    ): User {
        return withContext(Dispatchers.IO) {
            val user = updateUserUseCase(id, data, this)

            assert(user.isNotNull())

            user!!
        }
    }

    suspend fun deleteUser(id: String) {
        return withContext(Dispatchers.IO) {
            assert(deleteUserUseCase(id, this))
        }
    }
}