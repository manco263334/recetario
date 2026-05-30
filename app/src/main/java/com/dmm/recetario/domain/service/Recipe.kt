package com.dmm.recetario.domain.service

import com.dmm.recetario.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeService {
    suspend fun createRecipe(data: Recipe): Recipe

    fun getAllRecipes (
        page: Int,
        size: Int,
        withCategories: Boolean?,
        withCreator: Boolean?
    ): Flow<List<Recipe>>

    suspend fun syncRecipes (
        page: Int,
        size: Int,
        withCategories: Boolean?,
        withCreator: Boolean?
    ): Boolean

    suspend fun syncRecipe (
        id: String,
        withCategories: Boolean?,
        withCreator: Boolean?
    ): Boolean

    fun getRecipe(id: String): Flow<Recipe?>

    suspend fun updateRecipe (
        id: String,
        data: Recipe
    ): Recipe

    suspend fun deleteRecipe(id: String)
}