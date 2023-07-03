package com.example.cryptocurrencyapp.data.repository

import com.example.cryptocurrencyapp.utils.Resource
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser: FirebaseUser?
    suspend fun login(email: String, password: String): Flow<Resource<AuthResult>>
    suspend fun signUp(name: String, email: String, password: String): Flow<Resource<AuthResult>>

}