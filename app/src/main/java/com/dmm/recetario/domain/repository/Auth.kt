package com.dmm.recetario.domain.repository

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
    suspend fun login(data: LoginData): LoginResponse

    suspend fun register(data: RegisterData): LoginResponse

    suspend fun logout()

    suspend fun me(): MeResponse
}