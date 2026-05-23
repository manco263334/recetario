package com.dmm.recetario.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "recipes")
data class RecipeEntity (
    @PrimaryKey
    val id: String,
    val name: String,
    val persons: Int,
    val ingredients: List<Map<String, String>>,
    val steps: List<String>,
    val totalTimeInMinutes: Int,
    val cookingTimeInMinutes: Int,
    val preparationTimeInMinutes: Int,
    val stars: Int,
    val icon: String?,

    @ColumnInfo(name = "user_id")
    val userId: String
)

@Entity (
    tableName = "categories_recipes",
    primaryKeys = ["recipe_id", "category_id"]
)
data class RecipeCategoryCrossRef (
    @ColumnInfo(name = "recipe_id")
    val recipeId: String,

    @ColumnInfo(name = "category_id")
    val categoryId: String
)