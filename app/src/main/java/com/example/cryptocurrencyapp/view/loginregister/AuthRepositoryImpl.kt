package com.example.cryptocurrencyapp.view.loginregister

import android.util.Log
import com.example.cryptocurrencyapp.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import java.io.IOException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun login(email: String, password: String): Flow<Resource<FirebaseUser>> = flow {
        try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Log.e("asd",result.toString())
            emit((result.user?.let {
                Log.e("asd",it.toString())
                Resource.Success(it)
            }!!))
           // loggedOutLiveData.postValue(false)

        } catch (e: IOException) {
            emit(Resource.Failure(Exception(e.localizedMessage)))
        } catch (e: Exception) {
            emit(Resource.Failure(Exception(e.localizedMessage)))
        }
        }.flowOn(Dispatchers.IO)



    override suspend fun signup(name: String, email: String, password: String): Resource<FirebaseUser> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            result.user?.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(name).build())?.await()
            return Resource.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }

}