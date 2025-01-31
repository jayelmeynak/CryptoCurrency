package com.example.cryptocurrency.domain.model

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoinListCache @Inject constructor() {
    private var lastUpdated: Long = 0L
    private var cachedCoins: List<Coin> = emptyList()

    fun save(coins: List<Coin>) {
        cachedCoins = coins
        lastUpdated = System.currentTimeMillis()
    }

    fun load(): List<Coin> = cachedCoins

    fun isValid(): Boolean {
        val FIVE_MINUTES = 5 * 60 * 1000
        return (System.currentTimeMillis() - lastUpdated) < FIVE_MINUTES
    }
}