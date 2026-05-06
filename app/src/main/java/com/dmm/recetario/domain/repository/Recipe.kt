package com.dmm.recetario.domain.repository

import com.dmm.recetario.domain.model.Recipe
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface RecipeRepository {
    @POST("recipes")
    suspend fun createRecipe(data: Recipe): Response<Recipe>

    @GET("recipes")
    suspend fun getAllRecipes(): Response<List<Recipe>>

    @GET("recipes/{id}")
    suspend fun getRecipe(id: String): Response<Recipe>

    @PUT("recipes/{id}")
    suspend fun updateRecipe(id: String, data: Recipe): Response<Recipe>

    @DELETE("recipes/{id}")
    suspend fun deleteRecipe(id: String): Response<Unit>
}