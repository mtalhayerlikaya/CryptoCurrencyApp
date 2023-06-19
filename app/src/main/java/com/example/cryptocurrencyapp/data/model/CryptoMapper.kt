package com.example.cryptocurrencyapp.data.model

interface CryptoMapper<CryptoDetailResponseModel,CryptoModel> {
    fun mapToCryptoModel(cryptoDetailResponseModel: CryptoDetailResponseModel):CryptoModel
}