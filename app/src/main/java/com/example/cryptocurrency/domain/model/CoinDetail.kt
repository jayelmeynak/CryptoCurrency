package com.example.cryptocurrency.domain.model

data class CoinDetail(
    val coinId: String,
    val name: String,
    val symbol: String,
    val description: String,
    val rank: Int,
    val isActive: Boolean,
    val tags: List<String>,
    val team: List<TeamMember>,
)
