package com.dmm.recetario.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.dmm.recetario.data.local.database.entity.RecipeCategoryCrossRef
import com.dmm.recetario.data.local.database.entity.RecipeEntity
import com.dmm.recetario.data.local.database.entity.RecipeWithDetails

@Dao
interface RecipeDAO {
    @Transaction
    @Query("SELECT * FROM recipes")
    suspend fun getRecipes(): List<RecipeEntity>

    @Query("SELECT * FROM recipes WHERE id = :id")
    suspend fun getRecipe(id: String): RecipeEntity?

    @Upsert
    suspend fun saveRecipes(recipes: List<RecipeEntity>)

    @Upsert
    suspend fun insertReferences(refs: List<RecipeCategoryCrossRef>)

    @Query("DELETE FROM recipes")
    suspend fun clear()

    @Query("DELETE FROM recipes WHERE id = :id")
    suspend fun deleteRecipe(id: String)
}