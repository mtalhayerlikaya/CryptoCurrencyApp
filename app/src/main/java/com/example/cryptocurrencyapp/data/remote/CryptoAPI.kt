package com.example.cryptocurrencyapp.data.remote

import com.example.cryptocurrencyapp.data.model.CryptoDetailResponse
import com.example.cryptocurrencyapp.data.model.CryptoModel
import com.example.cryptocurrencyapp.utils.Constants.ALL_CRYPTOS
import com.example.cryptocurrencyapp.utils.Constants.BASE_URL
import com.example.cryptocurrencyapp.utils.Constants.CRYPTO
import retrofit2.Response
import retrofit2.http.*

interface CryptoAPI {

    @GET(ALL_CRYPTOS)
    suspend fun getAllCryptosFromAPI(): List<CryptoModel>


    @GET(CRYPTO)
    suspend fun getCrptoByID(@Path("id") id: String): CryptoDetailResponse
}