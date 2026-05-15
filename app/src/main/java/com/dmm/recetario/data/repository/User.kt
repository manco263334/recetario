package com.dmm.recetario.data.repository

import com.dmm.recetario.core.utils.mapper.toDomain
import com.dmm.recetario.data.local.database.dao.UserDAO
import com.dmm.recetario.data.remote.retrofit.APIUserService
import com.dmm.recetario.domain.model.User
import com.dmm.recetario.domain.repository.IUserRepository
import jakarta.inject.Inject

class UserRepository @Inject constructor (
    private val apiUserService: APIUserService,
    private val userDAO: UserDAO
): IUserRepository {
    override suspend fun getAllUsers(): List<User> {
        val response = apiUserService.getAllUsers()

        return response.body()?.content?.map { it.toDomain() } ?: emptyList()
    }

    override suspend fun getUser(id: String): User {
        val response = apiUserService.getUser(id)
        return response.body()!!.toDomain()
    }

    override suspend fun updateUser (
        id: String,
        data: User
    ): User {
        val response = apiUserService.updateUser(id, data)
        return response.body()!!.toDomain()
    }

    override suspend fun deleteUser(id: String) {
        apiUserService.deleteUser(id)
    }
}