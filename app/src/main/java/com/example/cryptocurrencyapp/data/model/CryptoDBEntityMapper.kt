package com.example.cryptocurrencyapp.data.model

import com.example.cryptocurrencyapp.data.mapper.EntityMapper

class CryptoDBEntityMapper : EntityMapper<CryptoModel, CryptoEntity> {
    override fun mapToEntity(cryptoModel: CryptoModel): CryptoEntity {
        return CryptoEntity(
            id = cryptoModel.cryptoId,
            symbol = cryptoModel.symbol,
            name = cryptoModel.name,
            image = cryptoModel.image,
            current_price = cryptoModel.current_price,
            price_change_percentage_24h = cryptoModel.price_change_percentage_24h,
            price_change_24h = cryptoModel.price_change_24h
        )
    }

    override fun mapToCryptoModel(cryptoEntity: CryptoEntity): CryptoModel {
        return CryptoModel(
            cryptoId = cryptoEntity.id,
            symbol = cryptoEntity.symbol,
            name = cryptoEntity.name,
            image = cryptoEntity.image,
            current_price = cryptoEntity.current_price,
            price_change_percentage_24h = cryptoEntity.price_change_percentage_24h,
            price_change_24h = cryptoEntity.price_change_24h
        )
    }

    fun toEntityList(initial: List<CryptoModel>): List<CryptoEntity> {
        return initial.map {
            mapToEntity(it)
        }
    }
    fun toCryptoList(initial: List<CryptoEntity>): List<CryptoModel> {
        return initial.map {
            mapToCryptoModel(it)
        }
    }
}