package com.dmm.recetario.domain.use_cases.category

import android.util.Log
import com.dmm.recetario.core.utils.extension.isNotNull
import com.dmm.recetario.core.utils.handler.APIException
import com.dmm.recetario.core.utils.mapper.toEntity
import com.dmm.recetario.data.local.database.dao.CategoryDAO
import com.dmm.recetario.data.repository.CategoryRepository
import com.dmm.recetario.domain.model.Category
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class CreateCategoryUseCase @Inject constructor (
    private val repository: CategoryRepository,
    private val dao: CategoryDAO
) {
    suspend operator fun invoke(data: Category, scope: CoroutineScope): Category? {
        var category: Category?

        try {
            category = repository.createCategory(data)
        } catch (e: APIException) {
            Log.w("CreateCategoryUseCase", "${e.message}")
            return null
        }

        if (category.isNotNull()) {
            scope.launch {
                dao.saveCategories(listOf(category.toEntity()))
            }
        }

        return category
    }
}