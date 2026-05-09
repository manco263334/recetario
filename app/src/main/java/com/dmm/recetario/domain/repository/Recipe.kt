package com.dmm.recetario.domain.repository

import com.dmm.recetario.domain.model.PageResponse
import com.dmm.recetario.domain.model.Recipe
import retrofit2.Response

interface RecipeRepository {
    suspend fun createRecipe(data: Recipe): Response<Recipe>

    suspend fun getAllRecipes(): Response<PageResponse<Recipe>>

    suspend fun getRecipe(id: String): Response<Recipe>

    suspend fun updateRecipe(id: String, data: Recipe): Response<Recipe>

    suspend fun deleteRecipe(id: String): Response<Unit>
}