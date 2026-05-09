package com.dmm.recetario.data.remote.retrofit

import com.dmm.recetario.domain.model.Category
import com.dmm.recetario.domain.model.PageResponse
import com.dmm.recetario.domain.repository.CategoryRepository
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface APICategoryService: CategoryRepository {
    @POST("categories")
    override suspend fun createCategory(@Body data: Category): Response<Category>

    @GET("categories")
    override suspend fun getAllCategories(): Response<PageResponse<Category>>

    @GET("categories/{id}")
    override suspend fun getCategory(@Path("id") id: String): Response<Category>

    @PUT("categories/{id}")
    override suspend fun updateCategory(@Path("id") id: String, @Body data: Category): Response<Category>

    @DELETE("categories/{id}")
    override suspend fun deleteCategory(@Path("id") id: String): Response<Unit>
}