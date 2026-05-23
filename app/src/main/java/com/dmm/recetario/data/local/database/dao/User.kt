package com.dmm.recetario.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.dmm.recetario.data.local.database.entity.RecipeEntity
import com.dmm.recetario.data.local.database.entity.TokenUserRef
import com.dmm.recetario.data.local.database.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDAO {
    @Query("SELECT * FROM users")
    fun getUsers(): Flow<List<UserEntity>>

    @Query("SELECT * FROM users WHERE id = :id")
    fun getUser(id: String): Flow<UserEntity?>

    @Query("SELECT * FROM users WHERE email = :email")
    fun getUserByEmail(email: String): Flow<UserEntity?>

    @Query("SELECT * FROM users WHERE username = :username")
    fun getUserByUsername(username: String): Flow<UserEntity?>

    @Query ("""
        SELECT u.* FROM tokens_users
        INNER JOIN users u ON u.email = tokens_users.user_email
        WHERE token = :token
    """)
    fun getUserByToken(token: String): Flow<UserEntity?>

    @Upsert
    fun insertReferences(refs: List<TokenUserRef>)

    @Query("DELETE FROM tokens_users WHERE token = :token")
    suspend fun deleteReference(token: String)

    @Query("DELETE FROM tokens_users")
    suspend fun clearReferences()

    @Upsert
    suspend fun saveUsers(users: List<UserEntity>)

    @Query("DELETE FROM users")
    suspend fun clear()

    @Query("DELETE FROM users WHERE id = :id")
    suspend fun deleteUser(id: String)

    @Transaction
    @Query ("""
            SELECT * FROM recipes
            WHERE user_id = :userId
            """)
    fun getRecipes(userId: String): Flow<List<RecipeEntity>>
}