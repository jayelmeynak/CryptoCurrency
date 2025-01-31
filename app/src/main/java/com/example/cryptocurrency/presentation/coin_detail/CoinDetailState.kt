package com.example.cryptocurrency.presentation.coin_detail

import com.example.cryptocurrency.domain.model.CoinDetail

sealed class CoinDetailState {
    object Loading : CoinDetailState()
    data class Success(
        val coin: CoinDetail,
        val isRefreshing: Boolean = false
    ) : CoinDetailState()
    data class Error(
        val message: String,
        val cachedData: CoinDetail? = null,
        val isRefreshing: Boolean
    ) : CoinDetailState()
}