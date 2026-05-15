package com.dmm.recetario.data.local.database

import androidx.room.Database
import androidx.room.InvalidationTracker
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dmm.recetario.data.local.database.dao.CategoryDAO
import com.dmm.recetario.data.local.database.dao.RecipeDAO
import com.dmm.recetario.data.local.database.dao.UserDAO
import com.dmm.recetario.data.local.database.entity.CategoryEntity
import com.dmm.recetario.data.local.database.entity.RecipeCategoryCrossRef
import com.dmm.recetario.data.local.database.entity.RecipeEntity
import com.dmm.recetario.data.local.database.entity.UserEntity

@Database (
    entities = [
        UserEntity::class,
        CategoryEntity::class,
        RecipeEntity::class,
        RecipeCategoryCrossRef::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun recipeDao(): RecipeDAO

    abstract fun categoryDao(): CategoryDAO

    abstract fun userDao(): UserDAO
}