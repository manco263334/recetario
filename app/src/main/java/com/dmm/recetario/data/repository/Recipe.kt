package com.dmm.recetario.data.repository

import com.dmm.recetario.core.utils.extension.isNotNull
import com.dmm.recetario.core.utils.handler.handleApiCall
import com.dmm.recetario.core.utils.mapper.toDomain
import com.dmm.recetario.data.remote.retrofit.APIRecipeService
import com.dmm.recetario.domain.model.Recipe
import com.dmm.recetario.domain.repository.IRecipeRepository
import jakarta.inject.Inject

class RecipeRepository @Inject constructor (
    private val apiRecipeService: APIRecipeService
): IRecipeRepository {
    override suspend fun createRecipe(data: Recipe): Recipe {
        val recipe = handleApiCall { apiRecipeService.createRecipe(data) }

        assert(recipe.isNotNull())

        return recipe!!.toDomain()
    }

    override suspend fun getAllRecipes(): List<Recipe> {
        val recipes = handleApiCall { apiRecipeService.getAllRecipes() }
        return recipes?.content?.map { it.toDomain() } ?: emptyList()
    }

    override suspend fun getRecipe(id: String): Recipe {
        val recipe = handleApiCall { apiRecipeService.getRecipe(id) }

        assert(recipe.isNotNull())

        return recipe!!.toDomain()
    }

    override suspend fun updateRecipe (
        id: String,
        data: Recipe
    ): Recipe {
        val recipe = handleApiCall { apiRecipeService.updateRecipe(id, data) }

        assert(recipe.isNotNull())

        return recipe!!.toDomain()
    }

    override suspend fun deleteRecipe(id: String) {
        handleApiCall { apiRecipeService.deleteRecipe(id) }
    }
}