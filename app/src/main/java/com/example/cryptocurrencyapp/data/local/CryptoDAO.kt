package com.example.cryptocurrencyapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cryptocurrencyapp.data.model.CryptoEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface CryptoDAO {
    /*@Query("SELECT * FROM crypto")
    suspend fun getAllCryptosFromDB(): List<CryptoEntity>

    @Query("SELECT * FROM crypto WHERE cryptoID = :id")
    suspend fun getCryptoByID(cryptoID: String): CryptoEntity*/



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCryptos(cryptos: List<CryptoEntity>)

    @Query("SELECT * FROM crypto")
    suspend fun getAllCryptos(): List<CryptoEntity>

    @Query("SELECT * FROM crypto WHERE name LIKE '%' || :searchPattern || '%' OR symbol LIKE '%' || :searchPattern || '%'")
    suspend fun findCryptoByName(searchPattern: String): List<CryptoEntity>

}