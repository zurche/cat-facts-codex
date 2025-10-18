package com.example.catfactscodex.data.remote

import retrofit2.http.GET

/**
 * Retrofit service for Cat Facts endpoint.
 */
interface CatFactApiService {
    @GET("fact")
    suspend fun getCatFact(): CatFactDto
}
