package com.dmm.recetario.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.dmm.recetario.data.local.database.entity.CategoryEntity
import com.dmm.recetario.data.local.database.entity.RecipeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDAO {
    @Query("SELECT * FROM categories")
    fun getCategories(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM categories WHERE id = :id")
    suspend fun getCategory(id: String): CategoryEntity?

    @Upsert
    suspend fun saveCategories(categories: List<CategoryEntity>)

    @Query("DELETE FROM categories")
    suspend fun clear()

    @Query("DELETE FROM categories WHERE id = :id")
    suspend fun deleteCategory(id: String)

    @Transaction
    @Query("""
            SELECT r.* FROM categories_recipes 
            INNER JOIN recipes as r ON r.id = recipe_id
            WHERE category_id = :categoryId
            """)
    fun getRecipes(categoryId: String): Flow<List<RecipeEntity>>
}