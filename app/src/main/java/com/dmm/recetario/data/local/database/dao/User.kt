package com.dmm.recetario.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.dmm.recetario.data.local.database.entity.UserEntity

@Dao
interface UserDAO {
    @Query("SELECT * FROM users")
    suspend fun getUsers(): List<UserEntity>

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUser(id: String): UserEntity?

    @Upsert
    suspend fun saveUsers(users: List<UserEntity>)

    @Query("DELETE FROM users")
    suspend fun clear()

    @Query("DELETE FROM users WHERE id = :id")
    suspend fun deleteUser(id: String)
}