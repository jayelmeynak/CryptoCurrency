package com.example.cryptocurrency.data.repository

import com.example.cryptocurrency.common.DomainException
import com.example.cryptocurrency.data.remote.CoinPaprikaApi
import com.example.cryptocurrency.data.remote.dto.toCoin
import com.example.cryptocurrency.data.remote.dto.toCoinDetail
import com.example.cryptocurrency.domain.model.Coin
import com.example.cryptocurrency.domain.model.CoinDetail
import com.example.cryptocurrency.domain.repository.CoinRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val api: CoinPaprikaApi
) : CoinRepository {
    override suspend fun getCoinById(coinId: String): CoinDetail {
        return try {
            api.getCoinById(coinId).toCoinDetail()
        } catch (e: HttpException) {
            throw DomainException.DomainNetworkException("API error: ${e.localizedMessage}")
        } catch (e: IOException) {
            throw DomainException.DomainNetworkException("Connection error")
        }
    }

    override suspend fun getCoins(): List<Coin> {
        return try {
            api.getCoins().map { it.toCoin() }
        } catch (e: HttpException) {
            throw DomainException.DomainNetworkException("API error: ${e.localizedMessage}")
        } catch (e: IOException) {
            throw DomainException.DomainNetworkException("Connection error")
        }
    }
}