package com.example.cryptocurrencyapp.data.remote

import com.example.cryptocurrencyapp.data.model.CryptoModel
import javax.inject.Inject

class RemoteCryptoDataImpl
@Inject
constructor(private val cryptoAPI: CryptoAPI) : RemoteCryptoData {

    override suspend fun getCryptoListFromAPI() = cryptoAPI.getAllCryptosFromAPI()

    override suspend fun getCoinByIdFromAPI(cryptoId: String) = cryptoAPI.getCrptoByID(cryptoId)
}