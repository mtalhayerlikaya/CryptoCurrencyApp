package com.example.cryptocurrencyapp.utils

object Constants {
    const val BASE_URL = "https://api.coingecko.com/api/v3/"
    const val ALL_CRYPTOS = "coins/markets?vs_currency=usd&order=market_cap_desc&per_page=100&page=1&sparkline=false&locale=en"
    const val CRYPTO = "coins/{id}?localization=false&tickers=false&market_data=true&community_data=false&developer_data=false&sparkline=false"
    const val FIREBASE_FAVOURITES = "crypto_favourites"

}