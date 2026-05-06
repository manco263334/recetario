package com.dmm.recetario.di

import com.dmm.recetario.domain.repository.AuthRepository
import com.dmm.recetario.domain.repository.CategoryRepository
import com.dmm.recetario.domain.repository.RecipeRepository
import com.dmm.recetario.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "http://10.0.2.2:8080/api/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    @Singleton
    @Provides
    fun provideRecipeRepository(retrofit: Retrofit): RecipeRepository {
        return retrofit.create(RecipeRepository::class.java)
    }

    @Singleton
    @Provides
    fun provideCategoryRepository(retrofit: Retrofit): CategoryRepository {
        return retrofit.create(CategoryRepository::class.java)
    }

    @Singleton
    @Provides
    fun provideUserRepository(retrofit: Retrofit): UserRepository {
        return retrofit.create(UserRepository::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthRepository(retrofit: Retrofit): AuthRepository {
        return retrofit.create(AuthRepository::class.java)
    }
}