package com.example.catfactscodex.data

import com.example.catfactscodex.data.remote.CatFactApiService
import com.example.catfactscodex.domain.model.CatFact
import com.example.catfactscodex.domain.repository.CatFactRepository

class CatFactRepositoryImpl(
    private val apiService: CatFactApiService
) : CatFactRepository {

    override suspend fun getRandomCatFact(): CatFact = apiService.getCatFact().toDomain()
}
