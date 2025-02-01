package com.example.cryptocurrency.presentation.coin_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cryptocurrency.domain.model.CoinDetail
import com.example.cryptocurrency.presentation.coin_detail.components.CoinTag
import com.example.cryptocurrency.presentation.coin_detail.components.TeamListItem
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun CoinDetailScreen(
    viewModel: CoinDetailViewModel = hiltViewModel(),
    outerPadding: PaddingValues
) {
    val state = viewModel.state.value
    when (state) {
        is CoinDetailState.Loading -> {
            CoinDetailLoadingScreen(outerPadding = outerPadding)
        }

        is CoinDetailState.Error -> {
            CoinDetailScreenError(
                outerPadding = outerPadding,
                viewModel = viewModel
            )
        }

        is CoinDetailState.Success -> {
            CoinDetailScreenSuccess(
                outerPadding = outerPadding,
                coin = state.coin
            )
        }
    }
}

@Composable
fun CoinDetailScreenSuccess(
    outerPadding: PaddingValues,
    coin: CoinDetail
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(outerPadding)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(20.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier.weight(8f),
                        text = "${coin.rank}. ${coin.name} (${coin.symbol})",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .weight(2f),
                        text = if (coin.isActive) "active" else "inactive",
                        color = if (coin.isActive) Color.Green else Color.Red,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.End,
                        fontStyle = FontStyle.Italic
                    )
                }
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = coin.description,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "Tags",
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.height(15.dp))
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    mainAxisSpacing = 10.dp,
                    crossAxisSpacing = 10.dp
                ) {
                    coin.tags.forEach { tag ->
                        CoinTag(tag = tag)
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "Team members",
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.height(15.dp))
            }
            items(coin.team) { teamMember ->
                TeamListItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    teamMember = teamMember
                )
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun CoinDetailScreenError(
    outerPadding: PaddingValues,
    viewModel: CoinDetailViewModel
) {
    val state = viewModel.state.value as CoinDetailState.Error
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
fun CoinDetailLoadingScreen(
    outerPadding: PaddingValues,
) {
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
