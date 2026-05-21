package com.dmm.recetario.data.service

import android.util.Log
import com.dmm.recetario.core.utils.extension.isNotNull
import com.dmm.recetario.core.utils.handler.APIException
import com.dmm.recetario.core.utils.mapper.toDomain
import com.dmm.recetario.core.utils.mapper.toEntity
import com.dmm.recetario.data.local.database.dao.UserDAO
import com.dmm.recetario.data.repository.UserRepository
import com.dmm.recetario.domain.model.User
import com.dmm.recetario.domain.use_cases.user.DeleteUserUseCase
import com.dmm.recetario.domain.use_cases.user.GetUserUseCase
import com.dmm.recetario.domain.use_cases.user.UpdateUserUseCase
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class UserService @Inject constructor (
    private val getUserUseCase: GetUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val userDAO: UserDAO,
    private val userRepository: UserRepository
) {
    fun getAllUsers(): Flow<List<User>> {
        return userDAO.getUsers().map {
            it.map { entity ->
                entity.toDomain()
            }
        }
    }

    suspend fun syncUsers() {
        try {
            val users = userRepository.getAllUsers()
            userDAO.saveUsers(users.map { it.toEntity() })
        } catch (e: APIException) {
            Log.e("UserService", "Error syncing users: ${e.message}", e)
        }
    }

    fun getRecipes() {

    }

    suspend fun getUser(id: String): User {
        return withContext(Dispatchers.IO) {
            val user = getUserUseCase(id, this)

            assert(user.isNotNull())

            user!!
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