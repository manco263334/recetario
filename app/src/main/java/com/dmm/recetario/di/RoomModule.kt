package com.dmm.recetario.di

import android.content.Context
import androidx.room.Room
import com.dmm.recetario.data.local.database.AppDatabase
import com.dmm.recetario.data.local.database.Converters
import com.dmm.recetario.data.local.database.dao.CategoryDAO
import com.dmm.recetario.data.local.database.dao.RecipeDAO
import com.dmm.recetario.data.local.database.dao.UserDAO
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    private const val APP_DATABASE_NAME = "recetario.db"

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context, gson: Gson): AppDatabase {
        return Room
            .databaseBuilder(context, AppDatabase::class.java, APP_DATABASE_NAME)
            .addTypeConverter(Converters(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideUserDAO(db: AppDatabase): UserDAO {
        return db.userDao()
    }

    @Provides
    @Singleton
    fun provideCategoryDAO(db: AppDatabase): CategoryDAO {
        return db.categoryDao()
    }

    @Provides
    @Singleton
    fun provideRecipeDAO(db: AppDatabase): RecipeDAO {
        return db.recipeDao()
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return Gson()
    }
}