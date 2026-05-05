package com.dmm.recetario.domain.repository

import com.dmm.recetario.domain.model.User

interface UserRepository {
    fun createUser(data: User): User

    fun getAllUsers(): List<User>

    fun getUser(id: String): User

    fun updateUser(id: String, data: User): User

    fun deleteUser(id: String)
}