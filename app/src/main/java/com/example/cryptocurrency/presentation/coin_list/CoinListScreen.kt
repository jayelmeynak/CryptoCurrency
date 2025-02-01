package com.example.cryptocurrency.presentation.coin_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cryptocurrency.domain.model.Coin
import com.example.cryptocurrency.presentation.coin_list.components.CoinListItem

@Composable
fun CoinListScreen(
    viewModel: CoinListViewModel = hiltViewModel(),
    outerPadding: PaddingValues,
    onItemClick: (Coin) -> Unit
) {
    val state = viewModel.state
    when (state.value) {
        is CoinListState.Loading -> {
            CoinListLoadingScreen(outerPadding)
        }

        is CoinListState.Error -> {
            CoinListScreenError(
                outerPadding,
                viewModel
            )
        }

        is CoinListState.Success -> {
            CoinListScreenSuccess(
                outerPadding = outerPadding,
                viewModel = viewModel,
                onItemClick = {
                    onItemClick(it)
                }
            )
        }

        is CoinListState.Empty -> {
            CoinListEmptyScreen(
                outerPadding
            )
        }
    }
}


@Composable
fun CoinListScreenSuccess(
    outerPadding: PaddingValues,
    viewModel: CoinListViewModel,
    onItemClick: (Coin) -> Unit
) {
    val state = viewModel.state.value as CoinListState.Success
    val coins = state.coins
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(outerPadding)
    ) {
        items(coins) { coin ->
            CoinListItem(coin = coin) {
                onItemClick(coin)
            }
        }
    }
}

@Composable
fun CoinListScreenError(
    outerPadding: PaddingValues,
    viewModel: CoinListViewModel
) {
    val state = viewModel.state.value as CoinListState.Error
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(outerPadding)
    ) {
        Text(
            text = state.message,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .align(Alignment.Center)
        )
    }
}

@Composable
fun CoinListLoadingScreen(outerPadding: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(outerPadding)
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun CoinListEmptyScreen(
    outerPadding: PaddingValues
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(outerPadding)
    ) {
        Text(
            text = "No coins found",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .align(Alignment.Center),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}