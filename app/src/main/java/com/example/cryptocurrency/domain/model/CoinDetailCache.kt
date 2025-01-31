package com.example.cryptocurrency.domain.model

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoinDetailCache @Inject constructor() {
    private var lastUpdated: Long = 0L
    private var cachedCoin: CoinDetail? = null

    fun save(coin: CoinDetail) {
        cachedCoin = coin
        lastUpdated = System.currentTimeMillis()
    }

    fun load(): CoinDetail? = cachedCoin

    fun isValid(): Boolean {
        val FIVE_MINUTES = 5 * 60 * 1000
        return (System.currentTimeMillis() - lastUpdated) < FIVE_MINUTES
    }
}