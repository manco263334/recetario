package com.dmm.recetario.domain.repository

import com.dmm.recetario.navigation.Recipe

interface RecipeRepository {
    fun createRecipe(data: Recipe): Recipe

    fun getAllRecipes(): List<Recipe>

    fun getRecipe(id: String): Recipe

    fun updateRecipe(id: String, data: Recipe): Recipe

    fun deleteRecipe(id: String)
}