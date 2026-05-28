package com.dmm.recetario.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.dmm.recetario.data.local.database.entity.CategoryEntity
import com.dmm.recetario.data.local.database.entity.RecipeCategoryCrossRef
import com.dmm.recetario.data.local.database.entity.RecipeEntity
import com.dmm.recetario.data.local.database.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDAO {
    @Transaction
    @Query("SELECT * FROM recipes")
    fun getRecipes(): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM recipes WHERE id = :id LIMIT 1")
    fun getRecipe(id: String): Flow<RecipeEntity?>

    @Upsert
    suspend fun saveRecipes(recipes: List<RecipeEntity>)

    @Upsert
    suspend fun insertReferences(refs: List<RecipeCategoryCrossRef>)

    @Query("DELETE FROM recipes")
    suspend fun clear()

    @Query("DELETE FROM recipes WHERE id = :id")
    suspend fun deleteRecipe(id: String)

    @Transaction
    @Query ("""
        SELECT u.* FROM users AS u
        INNER JOIN recipes AS r ON r.user_id = u.id
        WHERE r.id = :recipeId
        LIMIT 1
    """)
    fun getUser(recipeId: String): Flow<UserEntity?>

    @Transaction
    @Query ("""
        SELECT c.* FROM categories_recipes
        INNER JOIN categories AS c on c.id = category_id
        WHERE recipe_id = :recipeId
    """)
    fun getCategories(recipeId: String): Flow<List<CategoryEntity>>
}