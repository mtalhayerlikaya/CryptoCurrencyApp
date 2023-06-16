package com.example.cryptocurrencyapp.di

import android.content.Context
import androidx.room.Room
import com.example.cryptocurrencyapp.data.local.CryptoDAO
import com.example.cryptocurrencyapp.data.local.CryptoDatabase
import com.example.cryptocurrencyapp.data.local.LocalCryptoData
import com.example.cryptocurrencyapp.data.local.LocalCryptoDataImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object RoomModule {
    @Provides
    @Singleton
    fun provideLocalDataImpl(cryptoDAO: CryptoDAO): LocalCryptoData = LocalCryptoDataImpl(cryptoDAO)

    @Provides
    @Singleton
    fun provideDAO(cryptoDB: CryptoDatabase): CryptoDAO = cryptoDB.cryptoDao()

    @Provides
    @Singleton
    fun provideRoomDB(@ApplicationContext context: Context): CryptoDatabase =
        Room.databaseBuilder(
            context,
            CryptoDatabase::class.java,
            "crypto.db"
        ).build()
}