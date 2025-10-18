package com.example.catfactscodex.data.remote

import com.example.catfactscodex.domain.model.CatFact
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CatFactDto(
    @SerialName("fact") val fact: String,
    @SerialName("length") val length: Int
) {
    fun toDomain(): CatFact = CatFact(
        fact = fact,
        length = length
    )
}
