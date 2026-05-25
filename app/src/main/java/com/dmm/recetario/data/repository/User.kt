package com.dmm.recetario.data.repository

import com.dmm.recetario.core.utils.extension.isNotNull
import com.dmm.recetario.core.utils.handler.handleApiCall
import com.dmm.recetario.core.utils.mapper.toDomain
import com.dmm.recetario.data.remote.retrofit.APIUserService
import com.dmm.recetario.domain.model.User
import jakarta.inject.Inject

class UserRepository @Inject constructor (
    private val apiUserService: APIUserService
) {
    suspend fun getAllUsers (
        page: Int = 0,
        size: Int = 10,
        withRecipes: Boolean? = null
    ): List<User> {
        val users = handleApiCall {
            apiUserService.getAllUsers (
                page = page,
                size = size,
                withRecipes = withRecipes
            )
        }

        return users?.content?.map { it.toDomain() } ?: emptyList()
    }

    suspend fun getUser (
        id: String,
        withRecipes: Boolean? = null
    ): User {
        val user = handleApiCall {
            apiUserService.getUser (
                id = id,
                withRecipes = withRecipes
            )
        }

        assert(user.isNotNull())

        return user!!.toDomain()
    }

    suspend fun updateUser (
        id: String,
        data: User
    ): User {
        val user = handleApiCall { apiUserService.updateUser(id, data) }

        assert(user.isNotNull())

        return user!!.toDomain()
    }

    suspend fun deleteUser(id: String) {
        handleApiCall { apiUserService.deleteUser(id) }
    }
}