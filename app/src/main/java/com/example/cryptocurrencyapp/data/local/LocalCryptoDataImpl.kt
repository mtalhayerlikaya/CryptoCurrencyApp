package com.example.cryptocurrencyapp.data.local

import com.example.cryptocurrencyapp.data.model.CryptoEntity
import javax.inject.Inject

class LocalCryptoDataImpl
@Inject
constructor(private val cryptoDAO: CryptoDAO):LocalCryptoData {

    override suspend fun insertCryptoList(cryptoList: List<CryptoEntity>) =
        cryptoDAO.insertAllCryptos(cryptoList)

}