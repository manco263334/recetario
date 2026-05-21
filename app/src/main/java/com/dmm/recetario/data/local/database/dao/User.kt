package com.dmm.recetario.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.dmm.recetario.data.local.database.entity.RecipeEntity
import com.dmm.recetario.data.local.database.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDAO {
    @Query("SELECT * FROM users")
    fun getUsers(): Flow<List<UserEntity>>

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUser(id: String): UserEntity?

    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getUserByEmail(email: String): UserEntity?

    @Upsert
    suspend fun saveUsers(users: List<UserEntity>)

    @Query("DELETE FROM users")
    suspend fun clear()

    @Query("DELETE FROM users WHERE id = :id")
    suspend fun deleteUser(id: String)

    @Transaction
    @Query("""
            SELECT * FROM recipes
            WHERE user_id = :userId
            """)
    fun getRecipes(userId: String): Flow<List<RecipeEntity>>
}