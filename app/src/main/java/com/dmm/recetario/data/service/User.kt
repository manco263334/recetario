package com.dmm.recetario.data.service

import com.dmm.recetario.domain.model.User
import com.dmm.recetario.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserService @Inject constructor (
    private val repository: UserRepository
) {
    suspend fun createUser(data: User): User {
        return withContext(Dispatchers.IO) {
            val response = repository.createUser(data)
            response.body()!!
        }
    }

    suspend fun getAllUsers(): List<User> {
        return withContext(Dispatchers.IO) {
            val response = repository.getAllUsers()
            response.body()?.content ?: emptyList()
        }
    }

    suspend fun getUser(id: String): User {
        return withContext(Dispatchers.IO) {
            val response = repository.getUser(id)
            response.body()!!
        }
    }

    suspend fun updateUser (
        id: String,
        data: User
    ): User {
        return withContext(Dispatchers.IO) {
            val response = repository.updateUser(id, data)
            response.body()!!
        }
    }

    suspend fun deleteUser(id: String) {
        return withContext(Dispatchers.IO) {
            repository.deleteUser(id)
        }
    }
}