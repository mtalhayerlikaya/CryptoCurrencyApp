package com.example.cryptocurrencyapp.data.remote

import com.example.cryptocurrencyapp.data.model.CryptoDetailResponse
import com.example.cryptocurrencyapp.data.model.CryptoModel

interface RemoteCryptoData {
    suspend fun getCryptoListFromAPI(): List<CryptoModel>

    suspend fun getCoinByIdFromAPI(cryptoId: String): CryptoDetailResponse
}