package com.dmm.recetario.data.service

import com.dmm.recetario.core.utils.extension.isNotNull
import com.dmm.recetario.data.repository.RecipeRepository
import com.dmm.recetario.domain.model.Recipe
import com.dmm.recetario.domain.use_cases.recipe.CreateRecipeUseCase
import com.dmm.recetario.domain.use_cases.recipe.DeleteRecipeUseCase
import com.dmm.recetario.domain.use_cases.recipe.GetRecipeUseCase
import com.dmm.recetario.domain.use_cases.recipe.GetRecipesUseCase
import com.dmm.recetario.domain.use_cases.recipe.UpdateRecipeUseCase
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipeService @Inject constructor (
    private val createRecipeUseCase: CreateRecipeUseCase,
    private val getRecipesUseCase: GetRecipesUseCase,
    private val getRecipeUseCase: GetRecipeUseCase,
    private val updateRecipeUseCase: UpdateRecipeUseCase,
    private val deleteRecipeUseCase: DeleteRecipeUseCase
) {
    suspend fun createRecipe(data: Recipe): Recipe {
        return withContext(Dispatchers.IO) {
            val recipe = createRecipeUseCase(data, this)

            assert(recipe.isNotNull())

            recipe!!
        }
    }

    suspend fun getAllRecipes(): List<Recipe> {
        return withContext(Dispatchers.IO) {
            getRecipesUseCase(this)
        }
    }

    suspend fun getRecipe(id: String): Recipe {
        return withContext(Dispatchers.IO) {
            val recipe = getRecipeUseCase(id, this)

            assert(recipe.isNotNull())

            recipe!!
        }
    }

    suspend fun updateRecipe (
        id: String,
        data: Recipe
    ): Recipe {
        return withContext(Dispatchers.IO) {
            val recipe = updateRecipeUseCase(id, data, this)

            assert(recipe.isNotNull())

            recipe!!
        }
    }

    suspend fun deleteRecipe(id: String) {
        return withContext(Dispatchers.IO) {
            assert(deleteRecipeUseCase(id, this))
        }
    }
}