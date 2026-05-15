package com.dmm.recetario.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.dmm.recetario.data.local.database.entity.RecipeCategoryCrossRef
import com.dmm.recetario.data.local.database.entity.RecipeEntity
import com.dmm.recetario.data.local.database.entity.RecipeWithDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDAO {
    @Transaction
    @Query("SELECT * FROM recipes")
    fun getRecipes(): Flow<List<RecipeWithDetails>>

    @Query("SELECT * FROM recipes WHERE id = :id")
    fun getRecipe(id: String): Flow<RecipeWithDetails?>

    @Upsert
    suspend fun saveRecipes(recipes: List<RecipeEntity>)

    @Upsert
    suspend fun insertReferences(refs: List<RecipeCategoryCrossRef>)

    @Query("DELETE FROM recipes")
    fun clear()
}