package com.example.a220727_motasemesaefan_nycschools.presentation.di

import com.example.a220727_motasemesaefan_nycschools.data.api.SchoolApiService
import com.example.a220727_motasemesaefan_nycschools.data.api.SchoolApiService.Companion.BASE_URL
import com.example.a220727_motasemesaefan_nycschools.repository.SchoolRepository
import com.example.a220727_motasemesaefan_nycschools.repository.SchoolRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetModule {

    @Singleton
    @Provides
    fun provideSchoolApiService(): SchoolApiService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOkHttpClient())
            .build()
            .create(SchoolApiService::class.java)

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

    @Singleton
    @Provides
    fun provideSchoolRepository(): SchoolRepository =
        SchoolRepositoryImpl(provideSchoolApiService())
}