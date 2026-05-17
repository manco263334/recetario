package com.dmm.recetario.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.dmm.recetario.data.local.database.entity.CategoryEntity

@Dao
interface CategoryDAO {
    @Query("SELECT * FROM categories")
    suspend fun getCategories(): List<CategoryEntity>

    @Query("SELECT * FROM categories WHERE id = :id")
    suspend fun getCategory(id: String): CategoryEntity?

    @Upsert
    suspend fun saveCategories(categories: List<CategoryEntity>)

    @Query("DELETE FROM categories")
    suspend fun clear()

    @Query("DELETE FROM categories WHERE id = :id")
    suspend fun deleteCategory(id: String)
}