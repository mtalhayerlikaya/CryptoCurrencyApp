package com.example.cryptocurrencyapp.data

interface EntityMapper<NetworkModel,Entity> {
    fun mapToEntity(cryptoModel: NetworkModel):Entity
}