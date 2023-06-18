package com.example.cryptocurrencyapp.data.model

import com.google.gson.annotations.SerializedName

data class CryptoDetailResponse(
    @SerializedName("id")
    val cryptoId:String,
    @SerializedName("symbol")
    val symbol:String,
    @SerializedName("name")
    val name:String,
    @SerializedName("image")
    val image:Image,
    @SerializedName("market_data")
    val market_data: MarketData,
    @SerializedName("description")
    val description:Description,
    @SerializedName("hashing_algorithm")
    val hashing_algorithm:String
    )