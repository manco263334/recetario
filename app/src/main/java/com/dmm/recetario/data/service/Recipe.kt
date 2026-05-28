package com.dmm.recetario.data.service

import android.util.Log
import com.dmm.recetario.core.utils.extension.isNotNull
import com.dmm.recetario.core.utils.handler.APIException
import com.dmm.recetario.core.utils.mapper.toDomain
import com.dmm.recetario.core.utils.mapper.toEntity
import com.dmm.recetario.data.local.database.dao.RecipeDAO
import com.dmm.recetario.data.local.database.entity.RecipeCategoryCrossRef
import com.dmm.recetario.data.repository.RecipeRepository
import com.dmm.recetario.domain.model.Recipe
import com.dmm.recetario.domain.use_cases.recipe.CreateRecipeUseCase
import com.dmm.recetario.domain.use_cases.recipe.DeleteRecipeUseCase
import com.dmm.recetario.domain.use_cases.recipe.UpdateRecipeUseCase
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class RecipeService @Inject constructor (
    private val createRecipeUseCase: CreateRecipeUseCase,
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

    suspend fun syncRecipes (
        page: Int = 0,
        size: Int = 10,
        withCategories: Boolean? = null,
        withCreator: Boolean? = null
    ): Boolean {
        return try {
            val recipes = repository.getAllRecipes (
                page = page,
                size = size,
                withCategories = withCategories,
                withCreator = withCreator
            )

            dao.saveRecipes(recipes.map { it.toEntity() })

            if (withCategories == true) {
                recipes.forEach { recipe ->
                    dao.insertReferences(recipe.categories?.map { categoryId ->
                        RecipeCategoryCrossRef(recipe.id, categoryId)
                    } ?: emptyList())
                }
            }

            true
        } catch (e: APIException) {
            Log.e("RecipeService", "Error syncing recipes: ${e.message}", e)
            false
        }
    }

    suspend fun syncRecipe(id: String): Boolean {
        return try {
            val recipe = repository.getRecipe(id)

            dao.saveRecipe(recipe.toEntity())
            true
        } catch (e: APIException) {
            Log.e("RecipeService", "Error syncing recipe: ${e.message}", e)
            false
        }
    }

    fun getRecipe(id: String): Flow<Recipe?> {
        return dao.getRecipe(id).map { recipe ->
            recipe?.toDomain()
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