package com.dmm.recetario.data.repository

import com.dmm.recetario.core.utils.mapper.toDomain
import com.dmm.recetario.data.local.database.dao.RecipeDAO

import com.dmm.recetario.data.remote.retrofit.APIRecipeService
import com.dmm.recetario.domain.model.Recipe
import com.dmm.recetario.domain.repository.IRecipeRepository
import jakarta.inject.Inject

class RecipeRepository @Inject constructor (
    private val apiRecipeService: APIRecipeService,
    private val recipeDAO: RecipeDAO
): IRecipeRepository {
    override suspend fun createRecipe(data: Recipe): Recipe {
        val response = apiRecipeService.createRecipe(data)
        return response.body()!!.toDomain()
    }

    override suspend fun getAllRecipes(): List<Recipe> {
        val response = apiRecipeService.getAllRecipes()
        return response.body()?.content?.map { it.toDomain() } ?: emptyList()
    }

    override suspend fun getRecipe(id: String): Recipe {
        val response = apiRecipeService.getRecipe(id)
        return response.body()!!.toDomain()
    }

    override suspend fun updateRecipe (
        id: String,
        data: Recipe
    ): Recipe {
        val response = apiRecipeService.updateRecipe(id, data)
        return response.body()!!.toDomain()
    }

    override suspend fun deleteRecipe(id: String) {
        apiRecipeService.deleteRecipe(id)
    }
}