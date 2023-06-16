package com.example.cryptocurrencyapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cryptocurrencyapp.data.model.CryptoEntity

@Database(entities = [CryptoEntity::class], version = 1)
abstract class CryptoDatabase : RoomDatabase() {
    abstract fun userDao(): CryptoDAO
}