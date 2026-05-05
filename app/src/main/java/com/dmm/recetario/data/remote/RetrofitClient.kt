package com.dmm.recetario.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

// Define el modelo de datos (debe coincidir con tu DTO de Spring)
data class Task(
    val id: Long?,
    val description: String,
    val completed: Boolean
)

// La interfaz que define las llamadas a la API
interface TaskApiService {
    @GET("api/tasks")
    suspend fun getTasks(): List<Task>

    @POST("api/tasks")
    suspend fun createTask(@Body task: Task): Task
}

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8080/" // IP especial para el emulador de Android

    val instance: TaskApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TaskApiService::class.java)
    }
}