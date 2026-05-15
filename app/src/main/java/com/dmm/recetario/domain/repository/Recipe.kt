package com.dmm.recetario.domain.repository

import com.dmm.recetario.domain.model.Recipe

interface IRecipeRepository {
    suspend fun createRecipe(data: Recipe): Recipe

    suspend fun getAllRecipes(): List<Recipe>

    suspend fun getRecipe(id: String): Recipe

    suspend fun updateRecipe(id: String, data: Recipe): Recipe

    suspend fun deleteRecipe(id: String)
}