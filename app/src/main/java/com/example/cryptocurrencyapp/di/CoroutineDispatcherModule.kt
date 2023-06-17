package com.example.cryptocurrencyapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object CoroutineDispatcherModule {
    @Provides
    @Singleton
    fun provideDefaultCoroutineContext(): CoroutineDispatcher = Dispatchers.Default
}