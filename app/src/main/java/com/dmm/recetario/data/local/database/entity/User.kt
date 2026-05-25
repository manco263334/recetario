package com.dmm.recetario.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity (
    @PrimaryKey
    val id: String,
    val name: String,
    val email: String,
    val role: String,
    val phone: String?,
    val username: String?,
    val icon: String?,
)

@Entity (
    tableName = "tokens_users",
    primaryKeys = ["token", "email"]
)
data class TokenUserRef (
    val token: String,
    val email: String
)