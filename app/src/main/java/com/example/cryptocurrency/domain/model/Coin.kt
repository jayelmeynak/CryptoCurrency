package com.example.cryptocurrency.domain.model

data class Coin(
    val id: String,
    val name: String,
    val rank: Int,
    val symbol: String,
    val type: String,
    val isActive: Boolean,


)
