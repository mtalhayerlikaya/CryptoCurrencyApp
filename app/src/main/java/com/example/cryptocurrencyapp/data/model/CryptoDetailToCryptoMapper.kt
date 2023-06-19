package com.example.cryptocurrencyapp.data.model

class CryptoDetailToCryptoMapper : CryptoMapper<CryptoDetailResponse,CryptoModel> {
    override fun mapToCryptoModel(cryptoDetailResponseModel: CryptoDetailResponse): CryptoModel {
        return CryptoModel(
            cryptoId = cryptoDetailResponseModel.cryptoId,
            symbol = cryptoDetailResponseModel.symbol,
            name = cryptoDetailResponseModel.name,
            image = cryptoDetailResponseModel.image.large,
            current_price = cryptoDetailResponseModel.market_data.currentPrice.usd.toString(),
            price_change_percentage_24h = cryptoDetailResponseModel.market_data.price_change_percentage_24h.toString(),
            price_change_24h = cryptoDetailResponseModel.market_data.price_change_24h.toString()
        )
    }
}