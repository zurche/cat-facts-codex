package com.example.catfactscodex.domain.repository

import com.example.catfactscodex.domain.model.CatFact

interface CatFactRepository {
    suspend fun getRandomCatFact(): CatFact
}
