package com.dmm.recetario.domain.use_cases.category

import android.util.Log
import com.dmm.recetario.core.utils.handler.APIException
import com.dmm.recetario.data.local.database.dao.CategoryDAO
import com.dmm.recetario.data.repository.CategoryRepository
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DeleteCategoryUseCase @Inject constructor (
    private val repository: CategoryRepository,
    private val dao: CategoryDAO
) {
    suspend operator fun invoke(id: String, scope: CoroutineScope): Boolean {
        try {
            repository.deleteCategory(id)
        } catch (e: APIException) {
            Log.w("DeleteCategoryUseCase", "${e.message}")
            return false
        }

        scope.launch(Dispatchers.IO) {
            dao.deleteCategory(id)
        }

        return true
    }
}