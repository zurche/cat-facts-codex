package com.example.catfactscodex.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catfactscodex.domain.repository.CatFactRepository
import java.io.IOException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CatFactViewModel(
    private val repository: CatFactRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _uiState = MutableStateFlow<CatFactUiState>(CatFactUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        refreshCatFact()
    }

    fun refreshCatFact() {
        _uiState.value = CatFactUiState.Loading
        viewModelScope.launch(ioDispatcher) {
            runCatching { repository.getRandomCatFact() }
                .onSuccess { fact -> _uiState.value = CatFactUiState.Success(fact) }
                .onFailure { error -> _uiState.value = CatFactUiState.Error(error.toUiError()) }
        }
    }

    private fun Throwable.toUiError(): CatFactError = when (this) {
        is IOException -> CatFactError.Network
        else -> message?.takeIf { it.isNotBlank() }
            ?.let { CatFactError.Message(it) }
            ?: CatFactError.Unknown
    }
}
