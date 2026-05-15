package com.dmm.recetario.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.dmm.recetario.data.local.database.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDAO {
    @Query("SELECT * FROM categories")
    fun getCategories(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM categories WHERE id = :id")
    fun getCategory(id: String): Flow<CategoryEntity?>

    @Upsert
    suspend fun saveCategories(categories: List<CategoryEntity>)

    @Query("DELETE FROM categories")
    fun clear()
}