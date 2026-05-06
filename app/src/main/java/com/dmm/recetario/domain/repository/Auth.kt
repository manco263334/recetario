package com.dmm.recetario.domain.repository

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

data class LoginData (
    val email: String,
    val password: String
)

data class LoginResponse (
    val token: String,
    val name: String,
    val username: String?
)

data class RegisterData (
    val name: String,
    val email: String,
    val password: String,
    val phone: String?,
    val username: String?,
)

data class MeResponse (
    val id: String,
    val email: String,
    val name: String,
    val username: String?
)

interface AuthRepository {
    @POST("auth/login")
    suspend fun login(@Body data: LoginData): Response<LoginResponse>

    @POST("auth/register")
    suspend fun register(@Body data: RegisterData): Response<LoginResponse>

    @POST("auth/logout")
    suspend fun logout(): Response<Unit>

    @GET("auth/me")
    suspend fun me(): Response<MeResponse>
}