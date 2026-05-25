package com.dmm.recetario.data.repository

import com.dmm.recetario.core.utils.extension.isNotNull
import com.dmm.recetario.core.utils.handler.handleApiCall
import com.dmm.recetario.core.utils.mapper.toDomain
import com.dmm.recetario.data.remote.retrofit.APICategoryService
import com.dmm.recetario.domain.model.Category
import jakarta.inject.Inject

class CategoryRepository @Inject constructor (
    private val apiCategoryService: APICategoryService
) {
    suspend fun createCategory(data: Category): Category {
        val category = handleApiCall { apiCategoryService.createCategory(data) }

        assert(category.isNotNull())

        return category!!.toDomain()
    }

    suspend fun getAllCategories (
        page: Int = 0,
        size: Int = 10,
        withRecipes: Boolean? = null
    ): List<Category> {
        val categories = handleApiCall {
            apiCategoryService.getAllCategories (
                page = page,
                size = size,
                withRecipes = withRecipes
            )
        }

        return categories?.content?.map { it.toDomain() } ?: emptyList()
    }

    suspend fun getCategory (
        id: String,
        withRecipes: Boolean? = null
    ): Category {
        val category = handleApiCall {
            apiCategoryService.getCategory (
                id = id,
                withRecipes = withRecipes
            )
        }

        assert(category.isNotNull())

        return category!!.toDomain()
    }

    suspend fun updateCategory (
        id: String,
        data: Category
    ): Category {
        val category = handleApiCall { apiCategoryService.updateCategory(id, data) }

        assert(category.isNotNull())

        return category!!.toDomain()
    }

    suspend fun deleteCategory(id: String) {
        handleApiCall { apiCategoryService.deleteCategory(id) }
    }
}