package com.example.cryptocurrency.presentation.coin_list

import com.example.cryptocurrency.domain.model.Coin

sealed class CoinListState {
    object Loading : CoinListState()
    data class Success(val coins: List<Coin>, val isRefreshing: Boolean = false) : CoinListState()
    data class Error(
        val message: String,
        val cachedData: List<Coin>? = null,
        val isRefreshing: Boolean
    ) : CoinListState()

    object Empty : CoinListState()
}