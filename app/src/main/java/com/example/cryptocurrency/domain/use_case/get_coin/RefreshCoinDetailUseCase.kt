package com.example.cryptocurrency.domain.use_case.get_coin

import com.example.cryptocurrency.common.DomainException
import com.example.cryptocurrency.common.Resource
import com.example.cryptocurrency.domain.model.CoinDetail
import com.example.cryptocurrency.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RefreshCoinDetailUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(coinId: String): Flow<Resource<CoinDetail>> = flow {
        try {
            emit(Resource.Loading())
            val coin = repository.refreshCoinById(coinId)
            emit(Resource.Success(coin))
        } catch (e: DomainException.DomainNetworkException) {
            emit(Resource.Error(e.message ?: "Network error"))
        } catch (e: Exception) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}