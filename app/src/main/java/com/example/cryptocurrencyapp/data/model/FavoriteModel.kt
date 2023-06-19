package com.example.cryptocurrencyapp.data.model

data class FavoriteModel(

    val cryptoId: String = "",
    val symbol: String = "",
    val name: String = "",
    val image: String = "",
    val current_price: String = "",
    val price_change_24h: String = "",
    val price_change_percentage_24h: String = ""
)
