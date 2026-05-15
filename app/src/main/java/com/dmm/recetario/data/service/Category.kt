package com.dmm.recetario.data.service

import com.dmm.recetario.data.repository.CategoryRepository
import com.dmm.recetario.domain.model.Category
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CategoryService @Inject constructor (
    private val repository: CategoryRepository
) {
    suspend fun createCategory(data: Category): Category {
        return withContext(Dispatchers.IO) {
            val response = repository.createCategory(data)
            response
        }
    }

    suspend fun getAllCategories(): List<Category> {
        return withContext(Dispatchers.IO) {
            val response = repository.getAllCategories()
            response
        }
    }

    suspend fun getCategory(id: String): Category {
        return withContext(Dispatchers.IO) {
            val response = repository.getCategory(id)
            response
        }
    }

    suspend fun updateCategory (
        id: String,
        data: Category
    ): Category {
        return withContext(Dispatchers.IO) {
            val response = repository.updateCategory(id, data)
            response
        }
    }

    suspend fun deleteCategory(id: String) {
        return withContext(Dispatchers.IO) {
            repository.deleteCategory(id)
        }
    }
}