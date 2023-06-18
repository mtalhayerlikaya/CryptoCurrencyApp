package com.example.cryptocurrencyapp.data.model

import com.google.gson.annotations.SerializedName

data class MarketData(
    @SerializedName("current_price")
    val currentPrice: CurrencyType,
    @SerializedName("price_change_24h")
    val price_change_24h:Double,
    @SerializedName("price_change_percentage_24h")
    val price_change_percentage_24h:Double
)