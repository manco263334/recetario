package com.dmm.recetario.data.remote.retrofit

import com.dmm.recetario.domain.repository.AuthRepository
import com.dmm.recetario.domain.repository.LoginData
import com.dmm.recetario.domain.repository.LoginResponse
import com.dmm.recetario.domain.repository.MeResponse
import com.dmm.recetario.domain.repository.RegisterData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface APIAuthService: AuthRepository {
    @POST("auth/login")
    override suspend fun login(@Body data: LoginData): Response<LoginResponse>

    @POST("auth/register")
    override suspend fun register(@Body data: RegisterData): Response<LoginResponse>

    @POST("auth/logout")
    override suspend fun logout(): Response<Unit>

    @GET("auth/me")
    override suspend fun me(): Response<MeResponse>
}