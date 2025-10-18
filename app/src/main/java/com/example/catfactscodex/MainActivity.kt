package com.example.catfactscodex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.catfactscodex.ui.theme.CatFactsCodexTheme
import com.example.catfactscodex.ui.CatFactScreen
import com.example.catfactscodex.ui.CatFactViewModel
import com.example.catfactscodex.ui.CatFactViewModelFactory

class MainActivity : ComponentActivity() {

    private val viewModel: CatFactViewModel by viewModels {
        CatFactViewModelFactory(
            (application as CatFactsApplication).container.catFactRepository
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CatFactsCodexTheme {
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                Surface(modifier = Modifier.fillMaxSize()) {
                    CatFactScreen(
                        uiState = uiState,
                        onRefresh = viewModel::refreshCatFact
                    )
                }
            }
        }
    }
}
