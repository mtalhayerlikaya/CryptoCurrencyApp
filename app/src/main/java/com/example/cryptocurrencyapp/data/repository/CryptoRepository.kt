package com.example.cryptocurrencyapp.data.repository

import com.example.cryptocurrencyapp.Resource
import com.example.cryptocurrencyapp.data.model.CryptoDetailResponse
import com.example.cryptocurrencyapp.data.model.CryptoModel
import kotlinx.coroutines.flow.Flow

interface CryptoRepository {
    fun getAllCryptos(): Flow<Resource<List<CryptoModel>>>

    fun getCryptoByID(cryptoId: String): Flow<Resource<CryptoDetailResponse>>

    fun getCryptosCurrentPriceByID(delay: Long, cryptoId: String): Flow<Resource<Double>>
    fun logout()
}