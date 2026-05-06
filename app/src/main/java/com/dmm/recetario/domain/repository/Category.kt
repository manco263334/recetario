package com.dmm.recetario.domain.repository

import com.dmm.recetario.domain.model.Category
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface CategoryRepository {
    @POST("categories")
    suspend fun createCategory(data: Category): Response<Category>

    @GET("categories")
    suspend fun getAllCategories(): Response<List<Category>>

    @GET("categories/{id}")
    suspend fun getCategory(id: String): Response<Category>

    @PUT("categories/{id}")
    suspend fun updateCategory(id: String, data: Category): Response<Category>

    @DELETE("categories/{id}")
    suspend fun deleteCategory(id: String): Response<Unit>
}