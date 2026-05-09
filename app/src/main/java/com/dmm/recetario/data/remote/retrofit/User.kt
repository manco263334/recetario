package com.dmm.recetario.data.remote.retrofit

import com.dmm.recetario.domain.model.PageResponse
import com.dmm.recetario.domain.model.User
import com.dmm.recetario.domain.repository.UserRepository
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface APIUserService: UserRepository {
    @POST("users")
    override suspend fun createUser(@Body data: User): Response<User>

    @GET("users")
    override suspend fun getAllUsers(): Response<PageResponse<User>>

    @GET("users/{id}")
    override suspend fun getUser(@Path("id") id: String): Response<User>

    @PUT("users/{id}")
    override suspend fun updateUser(@Path("id") id: String, @Body data: User): Response<User>

    @DELETE("users/{id}")
    override suspend fun deleteUser(@Path("id") id: String): Response<Unit>
}