package com.dmm.recetario.data.repository

import com.dmm.recetario.core.utils.extension.isNotNull
import com.dmm.recetario.core.utils.handler.handleApiCall
import com.dmm.recetario.core.utils.mapper.toDomain
import com.dmm.recetario.data.remote.retrofit.APIUserService
import com.dmm.recetario.domain.model.User
import com.dmm.recetario.domain.repository.IUserRepository
import jakarta.inject.Inject

class UserRepository @Inject constructor (
    private val apiUserService: APIUserService
): IUserRepository {
    override suspend fun getAllUsers(): List<User> {
        val users = handleApiCall { apiUserService.getAllUsers() }

        return users?.content?.map { it.toDomain() } ?: emptyList()
    }

    override suspend fun getUser(id: String): User {
        val user = handleApiCall { apiUserService.getUser(id) }

        assert(user.isNotNull())

        return user!!.toDomain()
    }

    override suspend fun updateUser (
        id: String,
        data: User
    ): User {
        val user = handleApiCall { apiUserService.updateUser(id, data) }

        assert(user.isNotNull())

        return user!!.toDomain()
    }

    override suspend fun deleteUser(id: String) {
        handleApiCall { apiUserService.deleteUser(id) }
    }
}