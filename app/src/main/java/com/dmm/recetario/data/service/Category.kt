package com.dmm.recetario.data.service

import com.dmm.recetario.core.utils.extension.isNotNull
import com.dmm.recetario.domain.model.Category
import com.dmm.recetario.domain.use_cases.category.CreateCategoryUseCase
import com.dmm.recetario.domain.use_cases.category.DeleteCategoryUseCase
import com.dmm.recetario.domain.use_cases.category.GetCategoriesUseCase
import com.dmm.recetario.domain.use_cases.category.GetCategoryUseCase
import com.dmm.recetario.domain.use_cases.category.UpdateCategoryUseCase
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CategoryService @Inject constructor (
    private val createCategoryUseCase: CreateCategoryUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getCategoryUseCase: GetCategoryUseCase,
    private val updateCategoryUseCase: UpdateCategoryUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase
) {
    suspend fun createCategory(data: Category): Category {
        return withContext(Dispatchers.IO) {
            val category = createCategoryUseCase(data, this)

            assert(category.isNotNull())

            category!!
        }
    }

    suspend fun getAllCategories(): List<Category> {
        return withContext(Dispatchers.IO) {
            getCategoriesUseCase(this)
        }
    }

    suspend fun getCategory(id: String): Category {
        return withContext(Dispatchers.IO) {
            val category = getCategoryUseCase(id, this)

            assert(category.isNotNull())

            category!!
        }
    }

    suspend fun updateCategory (
        id: String,
        data: Category
    ): Category {
        return withContext(Dispatchers.IO) {
            val category = updateCategoryUseCase(id, data, this)

            assert(category.isNotNull())

            category!!
        }
    }

    suspend fun deleteCategory(id: String) {
        return withContext(Dispatchers.IO) {
            assert(deleteCategoryUseCase(id, this))
        }
    }
}