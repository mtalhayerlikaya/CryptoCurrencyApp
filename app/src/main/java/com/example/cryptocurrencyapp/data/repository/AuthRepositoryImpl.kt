package com.example.cryptocurrencyapp.data.repository

import com.example.cryptocurrencyapp.utils.Resource
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun login(email: String, password: String): Flow<Resource<AuthResult>> = flow {
        emit(Resource.Loading)
        emit(Resource.Success(firebaseAuth.signInWithEmailAndPassword(email, password).await()))
    }.catch {
        emit(Resource.Failure(it.message ?: it.localizedMessage))
    }


    override suspend fun signUp(name: String, email: String, password: String): Flow<Resource<AuthResult>> =flow {
        emit(Resource.Loading)
        val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        result.user?.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(name).build())?.await()
        emit(Resource.Success(result))


    }.catch {
        emit(Resource.Failure(it.message ?: it.localizedMessage))
    }



}