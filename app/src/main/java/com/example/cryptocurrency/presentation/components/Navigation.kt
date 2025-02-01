package com.example.cryptocurrency.presentation.components

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cryptocurrency.presentation.coin_detail.CoinDetailScreen
import com.example.cryptocurrency.presentation.coin_list.CoinListScreen

@Composable
fun Navigation(
    navController: NavHostController,
    outerPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Screen.CoinListScreen.route
    ) {
        composable(
            route = Screen.CoinListScreen.route
        ) {
            CoinListScreen(outerPadding = outerPadding) {
                Log.d("MyLog", "CoinListScreen: ${it.id}")
                navController.navigate(Screen.CoinDetailScreen.route + "/${it.id}")
            }
        }
        composable(
            route = Screen.CoinDetailScreen.route + "/{coinId}"
        ) {
            CoinDetailScreen(outerPadding = outerPadding)
        }
    }
}