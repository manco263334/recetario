package com.dmm.recetario.data.remote.retrofit

import com.dmm.recetario.data.model.dto.RecipeDTO
import com.dmm.recetario.domain.model.PageResponse
import com.dmm.recetario.domain.model.Recipe
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface APIRecipeService {
    @POST("recipes")
    suspend fun createRecipe(@Body data: Recipe): Response<RecipeDTO>

    @GET("recipes")
    suspend fun getAllRecipes (
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 10,
        @Query("withCategories") withCategories: Boolean? = null,
        @Query("withCreator") withCreator: Boolean? = null
    ): Response<PageResponse<RecipeDTO>>

    @GET("recipes/{id}")
    suspend fun getRecipe (
        @Path("id") id: String,
        @Query("withCategories") withCategories: Boolean? = null,
        @Query("withCreator") withCreator: Boolean? = null
    ): Response<RecipeDTO>

    @PUT("recipes/{id}")
    suspend fun updateRecipe (
        @Path("id") id: String,
        @Body data: Recipe
    ): Response<RecipeDTO>

    @DELETE("recipes/{id}")
    suspend fun deleteRecipe (
        @Path("id") id: String
    ): Response<Unit>
}