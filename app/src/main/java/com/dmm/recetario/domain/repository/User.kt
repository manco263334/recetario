package com.dmm.recetario.domain.repository

import com.dmm.recetario.domain.model.User

interface IUserRepository {
    suspend fun getAllUsers(): List<User>

    suspend fun getUser(id: String): User

    suspend fun updateUser(id: String, data: User): User

    suspend fun deleteUser(id: String)
}