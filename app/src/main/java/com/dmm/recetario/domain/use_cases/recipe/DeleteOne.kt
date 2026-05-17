package com.dmm.recetario.domain.use_cases.recipe

import android.util.Log
import com.dmm.recetario.core.utils.handler.APIException
import com.dmm.recetario.data.local.database.dao.RecipeDAO
import com.dmm.recetario.data.repository.RecipeRepository
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class DeleteRecipeUseCase @Inject constructor (
    private val repository: RecipeRepository,
    private val dao: RecipeDAO
) {
    suspend operator fun invoke(id: String, scope: CoroutineScope): Boolean {
        try {
            repository.deleteRecipe(id)
        } catch (e: APIException) {
            Log.w("DeleteRecipeUseCase", "${e.message}")
            return false
        }

        scope.launch {
            dao.deleteRecipe(id)
        }

        return true
    }
}