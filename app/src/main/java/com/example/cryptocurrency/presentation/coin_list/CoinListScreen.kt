package com.example.cryptocurrency.presentation.coin_list

import androidx.compose.foundation.layout.Box
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
    onItemClick: (Coin) -> Unit
) {
    val state = viewModel.state
    when (state.value) {
        is CoinListState.Loading -> {
            CoinListLoadingScreen()
        }

        is CoinListState.Error -> {
            CoinListScreenError()
        }

        is CoinListState.Success -> {
            CoinListScreenSuccess(
                viewModel = viewModel,
                onItemClick = {
                    onItemClick(it)
                }
            )
        }

        is CoinListState.Empty -> {
            CoinListEmptyScreen()
        }
    }
}


@Composable
fun CoinListScreenSuccess(
    viewModel: CoinListViewModel = hiltViewModel(),
    onItemClick: (Coin) -> Unit
) {
    val state = viewModel.state.value as CoinListState.Success
    val coins = state.coins
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(coins) { coin ->
            CoinListItem(coin = coin){
                onItemClick(coin)
            }
        }
    }
}

@Composable
fun CoinListScreenError(
    viewModel: CoinListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value as CoinListState.Error
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = state.message,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 20.dp).align(Alignment.Center)
        )
    }
}

@Composable
fun CoinListLoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun CoinListEmptyScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "No coins found",
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 20.dp).align(Alignment.Center),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}