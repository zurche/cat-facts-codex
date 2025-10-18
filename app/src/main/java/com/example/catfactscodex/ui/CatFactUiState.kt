package com.example.catfactscodex.ui

import com.example.catfactscodex.domain.model.CatFact

sealed interface CatFactUiState {
    data object Loading : CatFactUiState
    data class Success(val fact: CatFact) : CatFactUiState
    data class Error(val error: CatFactError) : CatFactUiState
}

sealed interface CatFactError {
    data object Network : CatFactError
    data object Unknown : CatFactError
    data class Message(val value: String) : CatFactError
}
