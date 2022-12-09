package com.vladrusakov.tradernet.presentation.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vladrusakov.tradernet.presentation.composable.TickerCell
import com.vladrusakov.tradernet.presentation.composable.onScreenStart
import com.vladrusakov.tradernet.presentation.navigation.Screen

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {

    val state = viewModel.uiState.collectAsState()
    onScreenStart(screen = Screen.Home, start = { viewModel.subscribeToSocketEvents() })

    HomeBody(
        modifier = modifier,
        state = state.value,
    )
}

@Composable
private fun HomeBody(
    modifier: Modifier = Modifier,
    state: HomeScreenUiState,
) {
    Scaffold { innerPadding ->

        Column(
            modifier = modifier
                .padding(innerPadding)
        ) {
            Spacer(modifier = Modifier.size(16.dp))
            if (state.homeState is HomeUiState.Success) {
                state.homeState.tickers.forEach { ticker ->
                    TickerCell(ticker = ticker)
                }
            } else {
                // Show error
            }
        }
    }

}

@Preview
@Composable
fun HomeBodyPreview() {

}
