package com.example.cryptocurrencyapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cryptocurrencyapp.data.model.CryptoEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface CryptoDAO {
    @Query("SELECT * FROM crypto")
    suspend fun getAllCryptosFromDB(): List<CryptoEntity>

    @Query("DELETE FROM crypto")
    suspend fun deleteAllRowsFromDB()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCryptos(cryptos: List<CryptoEntity>)

/*    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<CryptoEntity>

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): CryptoEntity

    @Insert
    fun insertAll(vararg users: CryptoEntity)

    @Delete
    fun delete(user: CryptoEntity)*/
}