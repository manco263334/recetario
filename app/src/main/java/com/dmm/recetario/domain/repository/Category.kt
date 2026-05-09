package com.dmm.recetario.domain.repository

import com.dmm.recetario.domain.model.Category
import com.dmm.recetario.domain.model.PageResponse
import retrofit2.Response

interface CategoryRepository {
    suspend fun createCategory(data: Category): Response<Category>

    suspend fun getAllCategories(): Response<PageResponse<Category>>

    suspend fun getCategory(id: String): Response<Category>

    suspend fun updateCategory(id: String, data: Category): Response<Category>

    suspend fun deleteCategory(id: String): Response<Unit>
}