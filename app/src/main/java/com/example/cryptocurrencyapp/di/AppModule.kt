package com.example.cryptocurrencyapp.di

import com.example.cryptocurrencyapp.data.local.LocalCryptoData
import com.example.cryptocurrencyapp.data.remote.CryptoAPI
import com.example.cryptocurrencyapp.data.remote.RemoteCryptoData
import com.example.cryptocurrencyapp.data.remote.RemoteCryptoDataImpl
import com.example.cryptocurrencyapp.data.repository.CryptoRepository
import com.example.cryptocurrencyapp.data.repository.CryptoRepositoryImpl
import com.example.cryptocurrencyapp.view.loginregister.AuthRepository
import com.example.cryptocurrencyapp.view.loginregister.AuthRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providesAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl

    @Provides
    @Singleton
    fun providesCryptoDataRepository(cryptoAPI: CryptoAPI): RemoteCryptoData = RemoteCryptoDataImpl(cryptoAPI)

    @Provides
    @Singleton
    fun providesCryptoRepository(remoteCryptoData: RemoteCryptoData,
                                 localCryptoData: LocalCryptoData,
                                 dispatcher: CoroutineDispatcher): CryptoRepository = CryptoRepositoryImpl(remoteCryptoData,localCryptoData,dispatcher)

}