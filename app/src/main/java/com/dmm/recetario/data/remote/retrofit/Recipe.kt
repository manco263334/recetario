package com.dmm.recetario.data.remote.retrofit

import com.dmm.recetario.domain.model.PageResponse
import com.dmm.recetario.domain.model.Recipe
import com.dmm.recetario.domain.repository.RecipeRepository
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface APIRecipeService: RecipeRepository {
    @POST("recipes")
    override suspend fun createRecipe(@Body data: Recipe): Response<Recipe>

    @GET("recipes")
    override suspend fun getAllRecipes(): Response<PageResponse<Recipe>>

    @GET("recipes/{id}")
    override suspend fun getRecipe(@Path("id") id: String): Response<Recipe>

    @PUT("recipes/{id}")
    override suspend fun updateRecipe(@Path("id") id: String, @Body data: Recipe): Response<Recipe>

    @DELETE("recipes/{id}")
    override suspend fun deleteRecipe(@Path("id") id: String): Response<Unit>
}