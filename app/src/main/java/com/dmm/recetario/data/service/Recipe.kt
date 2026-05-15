package com.dmm.recetario.data.service

import com.dmm.recetario.data.repository.RecipeRepository
import com.dmm.recetario.domain.model.Recipe
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipeService @Inject constructor (
    private val repository: RecipeRepository
) {
    suspend fun createRecipe(data: Recipe): Recipe {
        return withContext(Dispatchers.IO) {
            val response = repository.createRecipe(data)
            response
        }
    }

    suspend fun getAllRecipes(): List<Recipe> {
        return withContext(Dispatchers.IO) {
            val response = repository.getAllRecipes()
            response
        }
    }

    suspend fun getRecipe(id: String): Recipe {
        return withContext(Dispatchers.IO) {
            val response = repository.getRecipe(id)
            response
        }
    }

    suspend fun updateRecipe (
        id: String,
        data: Recipe
    ): Recipe {
        return withContext(Dispatchers.IO) {
            val response = repository.updateRecipe(id, data)
            response
        }
    }

    suspend fun deleteRecipe(id: String) {
        return withContext(Dispatchers.IO) {
            repository.deleteRecipe(id)
        }
    }
}