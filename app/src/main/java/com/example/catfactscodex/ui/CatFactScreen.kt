package com.example.catfactscodex.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.catfactscodex.R
import com.example.catfactscodex.domain.model.CatFact
import com.example.catfactscodex.ui.theme.CatFactsCodexTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatFactScreen(
    uiState: CatFactUiState,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CatFactsTopAppBar(scrollBehavior = scrollBehavior)
        },
        floatingActionButton = {
            Button(onClick = onRefresh) {
                Text(text = stringResource(id = R.string.cat_fact_new))
            }
        }
    ) { innerPadding ->
        Crossfade(
            targetState = uiState,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            label = "catFactUiState"
        ) { state ->
            when (state) {
                CatFactUiState.Loading -> LoadingState()
                is CatFactUiState.Error -> ErrorState(error = state.error, onRefresh = onRefresh)
                is CatFactUiState.Success -> SuccessState(fact = state.fact.fact)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CatFactsTopAppBar(scrollBehavior: TopAppBarScrollBehavior) {
    androidx.compose.material3.CenterAlignedTopAppBar(
        title = { Text(text = stringResource(id = R.string.cat_facts_title)) },
        scrollBehavior = scrollBehavior
    )
}

@Composable
private fun LoadingState() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorState(
    error: CatFactError,
    onRefresh: () -> Unit
) {
    val message = when (error) {
        CatFactError.Network -> stringResource(id = R.string.cat_fact_network_error)
        CatFactError.Unknown -> stringResource(id = R.string.cat_fact_generic_error)
        is CatFactError.Message -> error.value
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        Button(
            onClick = onRefresh,
            modifier = Modifier.padding(top = 24.dp)
        ) {
            Text(text = stringResource(id = R.string.cat_fact_retry))
        }
    }
}

@Composable
private fun SuccessState(fact: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = fact,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
private fun CatFactScreenPreviewLoading() {
    CatFactsCodexTheme {
        CatFactScreen(uiState = CatFactUiState.Loading, onRefresh = {})
    }
}

@Preview
@Composable
private fun CatFactScreenPreviewSuccess() {
    CatFactsCodexTheme {
        CatFactScreen(
            uiState = CatFactUiState.Success(
                CatFact(fact = "Cats can jump up to six times their length.", length = 48)
            ),
            onRefresh = {}
        )
    }
}

@Preview
@Composable
private fun CatFactScreenPreviewError() {
    CatFactsCodexTheme {
        CatFactScreen(
            uiState = CatFactUiState.Error(CatFactError.Network),
            onRefresh = {}
        )
    }
}
