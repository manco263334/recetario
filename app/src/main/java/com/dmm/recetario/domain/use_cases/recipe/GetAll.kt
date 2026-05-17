package com.dmm.recetario.domain.use_cases.recipe

import android.util.Log
import com.dmm.recetario.core.utils.handler.APIException
import com.dmm.recetario.core.utils.mapper.toDomain
import com.dmm.recetario.core.utils.mapper.toEntity
import com.dmm.recetario.data.local.database.dao.RecipeDAO
import com.dmm.recetario.data.repository.RecipeRepository
import com.dmm.recetario.domain.model.Recipe
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class GetRecipesUseCase @Inject constructor (
    private val repository: RecipeRepository,
    private val dao: RecipeDAO
) {
    suspend operator fun invoke(scope: CoroutineScope): List<Recipe> {
        var recipes = emptyList<Recipe>()

        try {
            recipes = repository.getAllRecipes()
        } catch (e: APIException) {
            Log.w("GetRecipesUseCase", "${e.message}")
        }

        if (recipes.isNotEmpty()) {
            scope.launch {
                dao.saveRecipes(recipes.map { it.toEntity() })
            }
        } else {
            recipes = dao.getRecipes().map { it.toDomain() }
        }

        return recipes
    }
}