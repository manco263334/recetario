package com.dmm.recetario.data.repository

import com.dmm.recetario.core.utils.extension.isNotNull
import com.dmm.recetario.core.utils.handler.handleApiCall
import com.dmm.recetario.core.utils.mapper.toDomain
import com.dmm.recetario.data.remote.retrofit.APIRecipeService
import com.dmm.recetario.domain.model.Recipe
import jakarta.inject.Inject

class RecipeRepository @Inject constructor (
    private val apiRecipeService: APIRecipeService
) {
    suspend fun createRecipe (
        data: Recipe
    ): Recipe {
        val recipe = handleApiCall { apiRecipeService.createRecipe(data) }

        assert(recipe.isNotNull())

        return recipe!!.toDomain()
    }

    suspend fun getAllRecipes (
        page: Int = 0,
        size: Int = 10,
        withCategories: Boolean? = null,
        withCreator: Boolean? = null
    ): List<Recipe> {
        val recipes = handleApiCall {
            apiRecipeService.getAllRecipes (
                page = page,
                size = size,
                withCategories = withCategories,
                withCreator = withCreator
            )
        }

        return recipes?.content?.map { it.toDomain() } ?: emptyList()
    }

    suspend fun getRecipe (
        id: String,
        withCategories: Boolean? = null,
        withCreator: Boolean? = null
    ): Recipe {
        val recipe = handleApiCall {
            apiRecipeService.getRecipe (
                id = id,
                withCategories = withCategories,
                withCreator = withCreator
            )
        }

        assert(recipe.isNotNull())

        return recipe!!.toDomain()
    }

    suspend fun updateRecipe (
        id: String,
        data: Recipe
    ): Recipe {
        val recipe = handleApiCall { apiRecipeService.updateRecipe(id, data) }

        assert(recipe.isNotNull())

        return recipe!!.toDomain()
    }

    suspend fun deleteRecipe(id: String) {
        handleApiCall { apiRecipeService.deleteRecipe(id) }
    }
}