package com.dmm.recetario.data.service

import com.dmm.recetario.domain.model.Recipe
import com.dmm.recetario.domain.repository.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecipeService @Inject constructor (
    private val repository: RecipeRepository
) {
    suspend fun createRecipe(data: Recipe): Recipe {
        return withContext(Dispatchers.IO) {
            val response = repository.createRecipe(data)
            response.body()!!
        }
    }

    suspend fun getAllRecipes(): List<Recipe> {
        return withContext(Dispatchers.IO) {
            val response = repository.getAllRecipes()
            response.body() ?: emptyList()
        }
    }

    suspend fun getRecipe(id: String): Recipe {
        return withContext(Dispatchers.IO) {
            val response = repository.getRecipe(id)
            response.body()!!
        }
    }

    suspend fun updateRecipe (
        id: String,
        data: Recipe
    ): Recipe {
        return withContext(Dispatchers.IO) {
            val response = repository.updateRecipe(id, data)
            response.body()!!
        }
    }

    suspend fun deleteRecipe(id: String) {
        return withContext(Dispatchers.IO) {
            repository.deleteRecipe(id)
        }
    }
}