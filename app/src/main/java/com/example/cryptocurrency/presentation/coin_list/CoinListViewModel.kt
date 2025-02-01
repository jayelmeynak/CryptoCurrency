package com.example.cryptocurrency.presentation.coin_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocurrency.common.Resource
import com.example.cryptocurrency.domain.model.Coin
import com.example.cryptocurrency.domain.use_case.get_coins.GetCoinsUseCase
import com.example.cryptocurrency.domain.use_case.get_coins.RefreshCoinsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val getCoinsUseCase: GetCoinsUseCase,
    private val refreshCoinsUseCase: RefreshCoinsUseCase,
) : ViewModel() {

    private val _state = mutableStateOf<CoinListState>(CoinListState.Loading)
    val state: State<CoinListState> = _state

    init {
        loadCoins()
    }

    fun loadCoins() {
        viewModelScope.launch {
            getCoinsUseCase().collect { resource ->
                handleResource(resource)
            }
        }
    }

    fun refreshCoinList() {
        viewModelScope.launch {
            refreshCoinsUseCase().collect { resource ->
                handleResource(resource)
            }
        }
    }

    private fun handleResource(resource: Resource<List<Coin>>) {
        _state.value = when (resource) {
            is Resource.Loading -> handleLoadingState()
            is Resource.Success -> handleSuccessState(resource.data)
            is Resource.Error -> handleErrorState(resource)
        }
    }

    private fun handleLoadingState(): CoinListState {
        return when (val current = _state.value) {
            is CoinListState.Success -> current.copy(isRefreshing = true)
            is CoinListState.Error -> current.copy(isRefreshing = true)
            else -> CoinListState.Loading
        }
    }

    // Обработка успешного результата с проверкой данных
    private fun handleSuccessState(data: List<Coin>?): CoinListState {
        return when {
            data == null -> CoinListState.Error(
                message = "Ошибка загрузки данных",
                cachedData = getCachedData(),
                isRefreshing = false
            )
            data.isEmpty() -> CoinListState.Empty
            else -> CoinListState.Success(coins = data)
        }
    }

    // Обработка ошибок с сохранением кешированных данных
    private fun handleErrorState(resource: Resource.Error<List<Coin>>): CoinListState {
        val cachedData = getCachedData() ?: resource.data
        return CoinListState.Error(
            message = resource.message ?: "Неизвестная ошибка",
            cachedData = cachedData,
            isRefreshing = false
        )
    }

    // Получение кешированных данных из текущего состояния
    private fun getCachedData(): List<Coin>? {
        return when (val current = _state.value) {
            is CoinListState.Success -> current.coins
            is CoinListState.Error -> current.cachedData
            else -> null
        }
    }
}