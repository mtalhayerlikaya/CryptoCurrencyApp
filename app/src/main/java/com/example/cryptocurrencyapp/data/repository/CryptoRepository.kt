package com.example.cryptocurrencyapp.data.repository

import com.example.cryptocurrencyapp.utils.Resource
import com.example.cryptocurrencyapp.data.model.CryptoDetailResponse
import com.example.cryptocurrencyapp.data.model.CryptoModel
import com.example.cryptocurrencyapp.data.model.FavoriteModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface CryptoRepository {
    fun getAllCryptos(): Flow<Resource<List<CryptoModel>>>

    fun getCryptoByID(cryptoId: String): Flow<Resource<CryptoDetailResponse>>

    fun getCryptosCurrentPriceByID(delay: Long, cryptoId: String): Flow<Resource<Double>>
    fun addCryptoToFavorites(cryptoDetailResponse: CryptoDetailResponse): Flow<Resource<Task<Void>>>
    fun getAllFavoritesFromFireStore(): Flow<Resource<List<FavoriteModel>>>
    fun deleteFromFavorites(cryptoModel: FavoriteModel): Flow<Resource<Task<Void>>>
    fun getSearchedCryptoFromDB(searchPattern:String): Flow<Resource<List<CryptoModel>>>
    fun getCurrentUserFromFirabase(): Flow<Resource<FirebaseUser>>
    fun logout()
}