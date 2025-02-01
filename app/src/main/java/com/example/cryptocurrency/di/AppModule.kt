package com.example.cryptocurrency.di

import com.example.cryptocurrency.common.Constants.BASE_URL
import com.example.cryptocurrency.data.remote.CoinPaprikaApi
import com.example.cryptocurrency.data.repository.CoinRepositoryImpl
import com.example.cryptocurrency.domain.model.CoinDetailCache
import com.example.cryptocurrency.domain.model.CoinListCache
import com.example.cryptocurrency.domain.repository.CoinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePaprikaApi(): CoinPaprikaApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinPaprikaApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCoinRepository(
        api: CoinPaprikaApi,
        coinListCache: CoinListCache,
        coinDetailCache: CoinDetailCache
    ): CoinRepository {
        return CoinRepositoryImpl(api, coinListCache, coinDetailCache)
    }

    @Provides
    @Singleton
    fun provideCoinListCache(): CoinListCache = CoinListCache()

    @Provides
    @Singleton
    fun provideCoinDetailCache(): CoinDetailCache = CoinDetailCache()
}