package com.dmm.recetario.domain.repository

import com.dmm.recetario.domain.model.Recipe

interface RecipeRepository {
    suspend fun createRecipe (data: Recipe): Recipe

    suspend fun getAllRecipes (
        page: Int,
        size: Int,
        withCategories: Boolean?,
        withCreator: Boolean?
    ): List<Recipe>

    suspend fun getRecipe (
        id: String,
        withCategories: Boolean?,
        withCreator: Boolean?
    ): Recipe

    suspend fun updateRecipe (
        id: String,
        data: Recipe
    ): Recipe

    suspend fun deleteRecipe(id: String)
}