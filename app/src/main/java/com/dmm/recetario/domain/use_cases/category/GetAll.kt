package com.dmm.recetario.domain.use_cases.category

import android.util.Log
import com.dmm.recetario.core.utils.handler.APIException
import com.dmm.recetario.core.utils.mapper.toDomain
import com.dmm.recetario.core.utils.mapper.toEntity
import com.dmm.recetario.data.local.database.dao.CategoryDAO
import com.dmm.recetario.data.repository.CategoryRepository
import com.dmm.recetario.domain.model.Category
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GetCategoriesUseCase @Inject constructor (
    private val repository: CategoryRepository,
    private val dao: CategoryDAO
) {
    suspend operator fun invoke(scope: CoroutineScope): List<Category> {
        var categories = emptyList<Category>()

        try {
            categories = repository.getAllCategories()
        } catch (e: APIException) {
            Log.w("GetCategoriesUseCase", "${e.message}")
        }

        if (categories.isNotEmpty()) {
            scope.launch(Dispatchers.IO) {
                dao.saveCategories(categories.map { it.toEntity() })
            }
        } else {
           categories = dao.getCategories().map { it.toDomain() }
        }

        return categories
    }
}
