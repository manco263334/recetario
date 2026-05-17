package com.dmm.recetario.data.service

import com.dmm.recetario.core.utils.extension.isNotNull
import com.dmm.recetario.domain.model.User
import com.dmm.recetario.domain.use_cases.user.DeleteUserUseCase
import com.dmm.recetario.domain.use_cases.user.GetUserUseCase
import com.dmm.recetario.domain.use_cases.user.GetUsersUseCase
import com.dmm.recetario.domain.use_cases.user.UpdateUserUseCase
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserService @Inject constructor (
    private val getUsersUseCase: GetUsersUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
) {
    suspend fun getAllUsers(): List<User> {
        return withContext(Dispatchers.IO) {
            getUsersUseCase(this)
        }
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