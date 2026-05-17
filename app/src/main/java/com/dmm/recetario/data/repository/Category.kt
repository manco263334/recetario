package com.dmm.recetario.data.repository

import com.dmm.recetario.core.utils.extension.isNotNull
import com.dmm.recetario.core.utils.handler.handleApiCall
import com.dmm.recetario.core.utils.mapper.toDomain
import com.dmm.recetario.data.remote.retrofit.APICategoryService
import com.dmm.recetario.domain.model.Category
import com.dmm.recetario.domain.repository.ICategoryRepository
import jakarta.inject.Inject

class CategoryRepository @Inject constructor (
    private val apiCategoryService: APICategoryService
): ICategoryRepository {
    override suspend fun createCategory(data: Category): Category {
        val category = handleApiCall { apiCategoryService.createCategory(data) }

        assert(category.isNotNull())

        return category!!.toDomain()
    }

    override suspend fun getAllCategories(): List<Category> {
        val categories = handleApiCall { apiCategoryService.getAllCategories() }
        return categories?.content?.map { it.toDomain() } ?: emptyList()
    }

    override suspend fun getCategory(id: String): Category {
        val category = handleApiCall { apiCategoryService.getCategory(id) }

        assert(category.isNotNull())

        return category!!.toDomain()
    }

    override suspend fun updateCategory (
        id: String,
        data: Category
    ): Category {
        val category = handleApiCall { apiCategoryService.updateCategory(id, data) }

        assert(category.isNotNull())

        return category!!.toDomain()
    }

    override suspend fun deleteCategory(id: String) {
        handleApiCall { apiCategoryService.deleteCategory(id) }
    }
}