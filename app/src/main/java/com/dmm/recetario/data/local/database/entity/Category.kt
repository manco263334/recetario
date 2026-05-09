package com.dmm.recetario.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "categories")
data class CategoryEntity (
    @PrimaryKey
    val id: String,
    val name: String,
    val image: String?
)