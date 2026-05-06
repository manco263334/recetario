package com.dmm.recetario.domain.repository

import com.dmm.recetario.domain.model.User
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface UserRepository {
    @POST("users")
    suspend fun createUser(data: User): Response<User>

    @GET("users")
    suspend fun getAllUsers(): Response<List<User>>

    @GET("users/{id}")
    suspend fun getUser(id: String): Response<User>

    @PUT("users/{id}")
    suspend fun updateUser(id: String, data: User): Response<User>

    @DELETE("users/{id}")
    suspend fun deleteUser(id: String): Response<Unit>
}