package com.dmm.recetario.data.service

import android.util.Log
import com.dmm.recetario.core.utils.extension.isNotNull
import com.dmm.recetario.core.utils.handler.APIException
import com.dmm.recetario.core.utils.mapper.toDomain
import com.dmm.recetario.core.utils.mapper.toEntity
import com.dmm.recetario.data.local.database.dao.RecipeDAO
import com.dmm.recetario.data.repository.RecipeRepository
import com.dmm.recetario.domain.model.Recipe
import com.dmm.recetario.domain.use_cases.recipe.CreateRecipeUseCase
import com.dmm.recetario.domain.use_cases.recipe.DeleteRecipeUseCase
import com.dmm.recetario.domain.use_cases.recipe.GetRecipeUseCase
import com.dmm.recetario.domain.use_cases.recipe.UpdateRecipeUseCase
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class RecipeService @Inject constructor (
    private val createRecipeUseCase: CreateRecipeUseCase,
    private val getRecipeUseCase: GetRecipeUseCase,
    private val updateRecipeUseCase: UpdateRecipeUseCase,
    private val deleteRecipeUseCase: DeleteRecipeUseCase,
    private val repository: RecipeRepository,
    private val dao: RecipeDAO
) {
    suspend fun createRecipe(data: Recipe): Recipe {
        return withContext(Dispatchers.IO) {
            val recipe = createRecipeUseCase(data, this)

            assert(recipe.isNotNull())

            recipe!!
        }
    }

    fun getAllRecipes(): Flow<List<Recipe>> {
        return dao.getRecipes().map { recipes ->
            recipes.map { recipe ->
                recipe.toDomain()
            }
        }
    }

    suspend fun syncRecipes() {
        try {
            val recipes = repository.getAllRecipes()
            dao.saveRecipes(recipes.map { it.toEntity() })
        } catch (e: APIException) {
            Log.e("RecipeService", "Error syncing recipes: ${e.message}", e)
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