package com.dmm.recetario.data.service

import com.dmm.recetario.domain.model.Category
import com.dmm.recetario.domain.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CategoryService @Inject constructor (
    private val repository: CategoryRepository
) {
    suspend fun createCategory(data: Category): Category {
        return withContext(Dispatchers.IO) {
            val response = repository.createCategory(data)
            response.body()!!
        }
    }

    suspend fun getAllCategories(): List<Category> {
        return withContext(Dispatchers.IO) {
            val response = repository.getAllCategories()
            response.body()?.content ?: emptyList()
        }
    }

    suspend fun getCategory(id: String): Category {
        return withContext(Dispatchers.IO) {
            val response = repository.getCategory(id)
            response.body()!!
        }
    }

    suspend fun updateCategory (
        id: String,
        data: Category
    ): Category {
        return withContext(Dispatchers.IO) {
            val response = repository.updateCategory(id, data)
            response.body()!!
        }
    }

    suspend fun deleteCategory(id: String) {
        return withContext(Dispatchers.IO) {
            repository.deleteCategory(id)
        }
    }
}