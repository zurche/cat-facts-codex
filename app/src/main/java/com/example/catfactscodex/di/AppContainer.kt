package com.example.catfactscodex.di

import com.example.catfactscodex.data.CatFactRepositoryImpl
import com.example.catfactscodex.data.remote.CatFactApiService
import com.example.catfactscodex.domain.repository.CatFactRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create

interface AppContainer {
    val catFactRepository: CatFactRepository
}

class DefaultAppContainer : AppContainer {

    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val httpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BASIC
                }
            )
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://catfact.ninja/")
            .client(httpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    private val catFactApi: CatFactApiService by lazy {
        retrofit.create()
    }

    override val catFactRepository: CatFactRepository by lazy {
        CatFactRepositoryImpl(catFactApi)
    }
}
