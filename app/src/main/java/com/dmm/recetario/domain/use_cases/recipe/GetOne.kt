package com.dmm.recetario.domain.use_cases.recipe

import android.util.Log
import com.dmm.recetario.core.utils.extension.isNotNull
import com.dmm.recetario.core.utils.handler.APIException
import com.dmm.recetario.core.utils.mapper.toDomain
import com.dmm.recetario.core.utils.mapper.toEntity
import com.dmm.recetario.data.local.database.dao.RecipeDAO
import com.dmm.recetario.data.repository.RecipeRepository
import com.dmm.recetario.domain.model.Recipe
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class GetRecipeUseCase @Inject constructor (
    private val repository: RecipeRepository,
    private val dao: RecipeDAO
) {
    suspend operator fun invoke(id: String, scope: CoroutineScope): Recipe? {
        var recipe: Recipe? = null

        try {
            recipe = repository.getRecipe(id)
        } catch (e: APIException) {
            Log.w("GetRecipeUseCase", "${e.message}")
        }

        if (recipe.isNotNull()) {
            scope.launch {
                dao.saveRecipes(listOf(recipe.toEntity()))
            }
        } else {
            recipe = dao.getRecipe(id)?.toDomain()
        }

        return recipe
    }
}