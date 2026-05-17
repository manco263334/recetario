package com.dmm.recetario.domain.use_cases.recipe

import android.util.Log
import com.dmm.recetario.core.utils.extension.isNotNull
import com.dmm.recetario.core.utils.handler.APIException
import com.dmm.recetario.core.utils.mapper.toEntity
import com.dmm.recetario.data.local.database.dao.RecipeDAO
import com.dmm.recetario.data.repository.RecipeRepository
import com.dmm.recetario.domain.model.Recipe
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class UpdateRecipeUseCase @Inject constructor (
    private val repository: RecipeRepository,
    private val dao: RecipeDAO
) {
    suspend operator fun invoke(id: String, data: Recipe, scope: CoroutineScope): Recipe? {
        var recipe: Recipe?

        try {
            recipe = repository.updateRecipe(id, data)
        } catch (e: APIException) {
            Log.w("UpdateRecipeUseCase", "${e.message}")
            return null
        }

        if (recipe.isNotNull()) {
            scope.launch {
                dao.saveRecipes(listOf(recipe.toEntity()))
            }
        }

        return recipe
    }
}