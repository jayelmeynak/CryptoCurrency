package com.example.cryptocurrency.presentation.components

import com.example.cryptocurrency.common.Constants.COIN_DETAIL_ROUTE
import com.example.cryptocurrency.common.Constants.COIN_LIST_ROUTE

sealed class Screen(
    val route: String
) {
    object CoinListScreen: Screen(COIN_LIST_ROUTE)
    object CoinDetailScreen: Screen(COIN_DETAIL_ROUTE)
}