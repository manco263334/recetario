package com.dmm.recetario.di

import com.dmm.recetario.data.remote.interceptor.AuthInterceptor
import com.dmm.recetario.data.remote.retrofit.APIAuthService
import com.dmm.recetario.data.remote.retrofit.APICategoryService
import com.dmm.recetario.data.remote.retrofit.APIRecipeService
import com.dmm.recetario.data.remote.retrofit.APIUserService
import com.dmm.recetario.domain.repository.AuthRepository
import com.dmm.recetario.domain.repository.CategoryRepository
import com.dmm.recetario.domain.repository.RecipeRepository
import com.dmm.recetario.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
//    private const val BASE_URL = "http://10.0.2.2:8080/api/"
    private const val BASE_URL = "http://192.168.11.125:8080/api/"

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRecipeRepository(retrofit: Retrofit): RecipeRepository {
        return retrofit.create(APIRecipeService::class.java)
    }

    @Singleton
    @Provides
    fun provideCategoryRepository(retrofit: Retrofit): CategoryRepository {
        return retrofit.create(APICategoryService::class.java)
    }

    @Singleton
    @Provides
    fun provideUserRepository(retrofit: Retrofit): UserRepository {
        return retrofit.create(APIUserService::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthRepository(retrofit: Retrofit): AuthRepository {
        return retrofit.create(APIAuthService::class.java)
    }
}