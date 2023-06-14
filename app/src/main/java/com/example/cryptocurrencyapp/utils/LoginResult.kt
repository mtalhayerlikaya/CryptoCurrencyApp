package com.example.cryptocurrencyapp.utils

enum class LoginResult(val message: String) {
    OK("Successfully logged in"),
    INVALID_EMAIL("invalid e-mail"),
    INVALID_PASSWORD("invalid password");
}