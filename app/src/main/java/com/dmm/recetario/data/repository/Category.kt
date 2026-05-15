package com.dmm.recetario.data.repository

import com.dmm.recetario.core.utils.mapper.toDomain
import com.dmm.recetario.data.local.database.dao.CategoryDAO
import com.dmm.recetario.data.remote.retrofit.APICategoryService
import com.dmm.recetario.domain.model.Category
import com.dmm.recetario.domain.repository.ICategoryRepository
import jakarta.inject.Inject

class CategoryRepository @Inject constructor (
    private val apiCategoryService: APICategoryService,
    private val categoryDAO: CategoryDAO
): ICategoryRepository {
    override suspend fun createCategory(data: Category): Category {
        val response = apiCategoryService.createCategory(data)
        return response.body()!!.toDomain()
    }

    override suspend fun getAllCategories(): List<Category> {
        val response = apiCategoryService.getAllCategories()
        return response.body()?.content?.map { it.toDomain() } ?: emptyList()
    }

    override suspend fun getCategory(id: String): Category {
        val response = apiCategoryService.getCategory(id)
        return response.body()!!.toDomain()
    }

    override suspend fun updateCategory (
        id: String,
        data: Category
    ): Category {
        val response = apiCategoryService.updateCategory(id, data)
        return response.body()!!.toDomain()
    }

    override suspend fun deleteCategory(id: String) {
        apiCategoryService.deleteCategory(id)
    }
}