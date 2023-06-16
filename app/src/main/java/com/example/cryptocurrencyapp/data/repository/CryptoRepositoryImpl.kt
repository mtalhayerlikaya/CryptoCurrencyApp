package com.example.cryptocurrencyapp.data.repository

import com.example.cryptocurrencyapp.Resource
import com.example.cryptocurrencyapp.data.local.LocalCryptoData
import com.example.cryptocurrencyapp.data.model.CryptoDBEntityMapper
import com.example.cryptocurrencyapp.data.model.CryptoDetailResponse
import com.example.cryptocurrencyapp.data.model.CryptoModel
import com.example.cryptocurrencyapp.data.remote.RemoteCryptoData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CryptoRepositoryImpl
@Inject
constructor(
    private val remoteCryptoData: RemoteCryptoData,
    private val localCryptoData: LocalCryptoData
) : CryptoRepository {

    override fun getAllCryptos(): Flow<Resource<List<CryptoModel>>> = flow {
        emit(Resource.Loading)
        try {
            val result = remoteCryptoData.getCryptoListFromAPI()
            localCryptoData.insertCryptoList(CryptoDBEntityMapper().toEntityList(result))
            emit(Resource.Success(result))
        } catch (throwable: Throwable) {
            emit(Resource.Failure(throwable.message ?: throwable.localizedMessage))
        }
    }

    override fun getCryptoByID(cryptoId: String): Flow<Resource<CryptoDetailResponse>> = flow {
        emit(Resource.Loading)
        try {
            val result = remoteCryptoData.getCoinByIdFromAPI(cryptoId)
            emit(Resource.Success(result))
        } catch (throwable: Throwable) {
            emit(Resource.Failure(throwable.message ?: throwable.localizedMessage))
        }
    }

}