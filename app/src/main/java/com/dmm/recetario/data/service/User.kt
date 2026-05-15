package com.dmm.recetario.data.service

import com.dmm.recetario.data.repository.UserRepository
import com.dmm.recetario.domain.model.User
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserService @Inject constructor (
    private val repository: UserRepository
) {
    suspend fun getAllUsers(): List<User> {
        return withContext(Dispatchers.IO) {
            val response = repository.getAllUsers()
            response
        }
    }

    suspend fun getUser(id: String): User {
        return withContext(Dispatchers.IO) {
            val response = repository.getUser(id)
            response
        }
    }

    suspend fun updateUser (
        id: String,
        data: User
    ): User {
        return withContext(Dispatchers.IO) {
            val response = repository.updateUser(id, data)
            response
        }
    }

    suspend fun deleteUser(id: String) {
        return withContext(Dispatchers.IO) {
            repository.deleteUser(id)
        }
    }
}