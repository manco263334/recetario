package com.dmm.recetario.domain.repository

import com.dmm.recetario.navigation.Category

interface CategoryRepository {
    fun createCategory(data: Category): Category

    fun getAllCategories(): List<Category>

    fun getCategory(id: String): Category

    fun updateCategory(id: String, data: Category): Category

    fun deleteCategory(id: String)
}