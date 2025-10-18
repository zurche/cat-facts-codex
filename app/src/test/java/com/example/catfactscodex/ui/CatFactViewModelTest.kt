package com.example.catfactscodex.ui

import com.example.catfactscodex.MainDispatcherRule
import com.example.catfactscodex.domain.model.CatFact
import com.example.catfactscodex.domain.repository.CatFactRepository
import java.io.IOException
import kotlin.test.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CatFactViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @Test
    fun refreshCatFact_emitsSuccess_whenRepositoryReturnsFact() = runTest(dispatcherRule.dispatcher.scheduler) {
        val expectedFact = CatFact(fact = "Cats sleep for 70% of their lives.", length = 42)
        val viewModel = CatFactViewModel(
            repository = FakeCatFactRepository { expectedFact },
            ioDispatcher = dispatcherRule.dispatcher
        )

        advanceUntilIdle()

        assertEquals(
            CatFactUiState.Success(expectedFact),
            viewModel.uiState.value
        )
    }

    @Test
    fun refreshCatFact_emitsNetworkError_whenRepositoryThrowsIOException() = runTest(dispatcherRule.dispatcher.scheduler) {
        val viewModel = CatFactViewModel(
            repository = FakeCatFactRepository { throw IOException("timeout") },
            ioDispatcher = dispatcherRule.dispatcher
        )

        advanceUntilIdle()

        assertEquals(
            CatFactUiState.Error(CatFactError.Network),
            viewModel.uiState.value
        )
    }

    @Test
    fun refreshCatFact_emitsCustomMessage_whenRepositoryThrowsMessageException() = runTest(dispatcherRule.dispatcher.scheduler) {
        val viewModel = CatFactViewModel(
            repository = FakeCatFactRepository { throw IllegalStateException("Service down") },
            ioDispatcher = dispatcherRule.dispatcher
        )

        advanceUntilIdle()

        assertEquals(
            CatFactUiState.Error(CatFactError.Message("Service down")),
            viewModel.uiState.value
        )
    }
}

private class FakeCatFactRepository(
    private val block: suspend () -> CatFact
) : CatFactRepository {
    override suspend fun getRandomCatFact(): CatFact = block()
}
