package com.example.cryptocurrency.presentation.coin_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocurrency.common.Constants.PARAM_COIN_ID
import com.example.cryptocurrency.common.Resource
import com.example.cryptocurrency.domain.model.CoinDetail
import com.example.cryptocurrency.domain.use_case.get_coin.GetCoinUseCase
import com.example.cryptocurrency.domain.use_case.get_coin.RefreshCoinDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val getCoinUseCase: GetCoinUseCase,
    private val refreshCoinDetailUseCase: RefreshCoinDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf<CoinDetailState>(CoinDetailState.Loading)
    val state: State<CoinDetailState> = _state
    private val coinId: String = savedStateHandle[PARAM_COIN_ID] ?: throw IllegalArgumentException("coinId is missing")

    init {
        loadCoinDetail(coinId)
    }

    fun loadCoinDetail(coinId: String) {
        viewModelScope.launch {
            getCoinUseCase(coinId).collect { resource ->
                handleResource(resource)
            }
        }
    }

    fun refreshCoinDetail(coinId: String) {
        viewModelScope.launch {
            refreshCoinDetailUseCase(coinId).collect { resource ->
                handleResource(resource)
            }
        }
    }

    private fun handleResource(resource: Resource<CoinDetail>) {
        _state.value = when (resource) {
            is Resource.Loading -> handleLoadingState()
            is Resource.Success -> handleSuccessState(resource.data)
            is Resource.Error -> handleErrorState(resource)
        }
    }

    private fun handleLoadingState(): CoinDetailState {
        return when (val current = _state.value) {
            is CoinDetailState.Success -> current.copy(isRefreshing = true)
            is CoinDetailState.Error -> current.copy(isRefreshing = true)
            else -> CoinDetailState.Loading
        }
    }

    private fun handleSuccessState(data: CoinDetail?): CoinDetailState {
        return when {
            data == null -> CoinDetailState.Error(
                message = "Coin not found",
                isRefreshing = false,
                cachedData = getCachedData()
            )

            else -> CoinDetailState.Success(data)
        }
    }

    private fun handleErrorState(resource: Resource.Error<CoinDetail>): CoinDetailState {
        val cachedData = getCachedData() ?: resource.data

        return CoinDetailState.Error(
            message = resource.message ?: "An unexpected error occurred",
            cachedData = cachedData,
            isRefreshing = false
        )
    }

    private fun getCachedData(): CoinDetail? {
        return when (val current = _state.value) {
            is CoinDetailState.Success -> current.coin
            is CoinDetailState.Error -> current.cachedData
            else -> null
        }
    }
}