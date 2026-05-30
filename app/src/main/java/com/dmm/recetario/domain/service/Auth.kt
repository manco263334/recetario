package com.dmm.recetario.domain.service

import com.dmm.recetario.domain.repository.LoginData
import com.dmm.recetario.domain.repository.LoginResponse
import com.dmm.recetario.domain.repository.MeResponse
import com.dmm.recetario.domain.repository.RegisterData

interface AuthService {
    suspend fun login(data: LoginData): LoginResponse
    suspend fun register(data: RegisterData): LoginResponse
    suspend fun logout()
    suspend fun me(): MeResponse
}