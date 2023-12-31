package com.example.cryptocurrencyapp.data.mapper

interface EntityMapper<NetworkModel,Entity> {
    fun mapToEntity(cryptoModel: NetworkModel):Entity
    fun mapToCryptoModel(cryptoEntity: Entity):NetworkModel
}