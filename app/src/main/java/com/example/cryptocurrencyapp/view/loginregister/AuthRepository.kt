package com.example.cryptocurrencyapp.view.loginregister

import com.example.cryptocurrencyapp.Resource
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser: FirebaseUser?
    suspend fun login(email: String, password: String): Flow<Resource<FirebaseUser>>
    suspend fun signup(name: String, email: String, password: String): Resource<FirebaseUser>
    fun logout()
}