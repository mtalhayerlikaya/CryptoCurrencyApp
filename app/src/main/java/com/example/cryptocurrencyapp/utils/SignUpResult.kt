package com.example.cryptocurrencyapp.utils

enum class SignUpResult(val message: String) {
    OK("Successfully registered"),
    INVALID_EMAIL("invalid e-mail"),
    INVALID_PASSWORD("invalid password");
}