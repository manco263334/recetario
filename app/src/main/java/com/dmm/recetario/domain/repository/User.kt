package com.dmm.recetario.domain.repository

import com.dmm.recetario.domain.model.PageResponse
import com.dmm.recetario.domain.model.User
import retrofit2.Response

interface UserRepository {
    suspend fun createUser(data: User): Response<User>

    suspend fun getAllUsers(): Response<PageResponse<User>>

    suspend fun getUser(id: String): Response<User>

    suspend fun updateUser(id: String, data: User): Response<User>

    suspend fun deleteUser(id: String): Response<Unit>
}