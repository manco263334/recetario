package com.dmm.recetario.data.local.database.entity

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

    val userId: String
)

@Entity (
    tableName = "categories_recipes",
    primaryKeys = ["recipe_id", "category_id"]
)
data class RecipeCategoryCrossRef (
    val recipe_id: String,
    val category_id: String
)

data class RecipeWithDetails (
    @Embedded val recipe: RecipeEntity,

    @Relation (
        parentColumn = "userId",
        entityColumn = "id"
    )
    val creator: UserEntity,

    @Relation (
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction (
            RecipeCategoryCrossRef::class,
            parentColumn = "recipe_id",
            entityColumn = "category_id"
        )
    )
    val categories: List<CategoryEntity>
)