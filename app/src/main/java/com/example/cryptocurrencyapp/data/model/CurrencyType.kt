package com.example.cryptocurrencyapp.data.model

import com.google.gson.annotations.SerializedName

data class CurrencyType(
    @SerializedName("usd")
    val usd: Double
)
