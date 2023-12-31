package com.example.cryptocurrencyapp.data.local

import com.example.cryptocurrencyapp.data.model.CryptoEntity

interface LocalCryptoData {
    suspend fun insertCryptoList(cryptoList:List<CryptoEntity>)
    suspend fun getSearchedCryptoFromDB(searchPattern: String):List<CryptoEntity>
    suspend fun getAllCryptosFromDB():List<CryptoEntity>
}