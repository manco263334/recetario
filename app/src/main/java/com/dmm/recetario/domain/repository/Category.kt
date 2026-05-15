package com.dmm.recetario.domain.repository

import com.dmm.recetario.domain.model.Category

interface ICategoryRepository {
    suspend fun createCategory(data: Category): Category

    suspend fun getAllCategories(): List<Category>

    suspend fun getCategory(id: String): Category

    suspend fun updateCategory(id: String, data: Category): Category

    suspend fun deleteCategory(id: String)
}