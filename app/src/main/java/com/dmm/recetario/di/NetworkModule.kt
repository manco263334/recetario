package com.dmm.recetario.di

import com.dmm.recetario.data.remote.interceptor.AuthInterceptor
import com.dmm.recetario.data.remote.retrofit.APIAuthService
import com.dmm.recetario.data.remote.retrofit.APICategoryService
import com.dmm.recetario.data.remote.retrofit.APIRecipeService
import com.dmm.recetario.data.remote.retrofit.APIUserService
import com.dmm.recetario.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
//    private const val BASE_URL = "http://10.0.2.2:8080/api/"
    private const val BASE_URL = "http://192.168.11.125:8080/api/"
//    private const val BASE_URL = "http://192.168.1.125:8080/api/"
//    private const val BASE_URL = "http://192.168.1.96:8080/api/"
//    private const val BASE_URL = "http://192.168.33.29:8080/api/"

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
    fun provideAuthService(retrofit: Retrofit): APIAuthService {
        return retrofit.create(APIAuthService::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthRepository(retrofit: Retrofit): AuthRepository {
        return retrofit.create(AuthRepository::class.java)
    }

    @Singleton
    @Provides
    fun provideUserService(retrofit: Retrofit): APIUserService {
        return retrofit.create(APIUserService::class.java)
    }

    @Singleton
    @Provides
    fun provideCategoryService(retrofit: Retrofit): APICategoryService {
        return retrofit.create(APICategoryService::class.java)
    }

    @Singleton
    @Provides
    fun provideRecipeService(retrofit: Retrofit): APIRecipeService {
        return retrofit.create(APIRecipeService::class.java)
    }
}