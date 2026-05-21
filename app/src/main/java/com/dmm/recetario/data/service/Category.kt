package com.dmm.recetario.data.service

import android.util.Log
import com.dmm.recetario.core.utils.extension.isNotNull
import com.dmm.recetario.core.utils.handler.APIException
import com.dmm.recetario.core.utils.mapper.toDomain
import com.dmm.recetario.core.utils.mapper.toEntity
import com.dmm.recetario.data.local.database.dao.CategoryDAO
import com.dmm.recetario.data.repository.CategoryRepository
import com.dmm.recetario.domain.model.Category
import com.dmm.recetario.domain.model.Recipe
import com.dmm.recetario.domain.use_cases.category.CreateCategoryUseCase
import com.dmm.recetario.domain.use_cases.category.DeleteCategoryUseCase
import com.dmm.recetario.domain.use_cases.category.GetCategoryUseCase
import com.dmm.recetario.domain.use_cases.category.UpdateCategoryUseCase
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class CategoryService @Inject constructor (
    private val createCategoryUseCase: CreateCategoryUseCase,
    private val getCategoryUseCase: GetCategoryUseCase,
    private val updateCategoryUseCase: UpdateCategoryUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase,
    private val repository: CategoryRepository,
    private val dao: CategoryDAO
) {
    suspend fun createCategory(data: Category): Category {
        return withContext(Dispatchers.IO) {
            val category = createCategoryUseCase(data, this)

            assert(category.isNotNull())

            category!!
        }
    }

    fun getAllCategories(): Flow<List<Category>> {
        return dao.getCategories().map { entities ->
            entities.map { entity ->
                entity.toDomain()
            }
        }
    }

    fun getRecipes(categoryId: String): Flow<List<Recipe>> {
        return dao.getRecipes(categoryId).map { entities ->
            entities.map { entity ->
                entity.toDomain()
            }
        }
    }

    suspend fun syncCategories() {
        try {
            val categories = repository.getAllCategories()
            dao.saveCategories(categories.map { it.toEntity() })
        } catch (e: APIException) {
            Log.e("CategoryService", "Error syncing categories: ${e.message}", e)
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