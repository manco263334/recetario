package com.dmm.recetario.data.remote.retrofit

import com.dmm.recetario.data.model.dto.CategoryDTO
import com.dmm.recetario.domain.model.Category
import com.dmm.recetario.domain.model.PageResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface APICategoryService {
    @POST("categories")
    suspend fun createCategory (
        @Body data: Category
    ): Response<CategoryDTO>

    @GET("categories")
    suspend fun getAllCategories (
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 10,
        @Query("withRecipes") withRecipes: Boolean? = null
    ): Response<PageResponse<CategoryDTO>>

    @GET("categories/{id}")
    suspend fun getCategory (
        @Path("id") id: String,
        @Query("withRecipes") withRecipes: Boolean? = null
    ): Response<CategoryDTO>

    @PUT("categories/{id}")
    suspend fun updateCategory (
        @Path("id") id: String,
        @Body data: Category
    ): Response<CategoryDTO>

    @DELETE("categories/{id}")
    suspend fun deleteCategory (
        @Path("id") id: String
    ): Response<Unit>
}