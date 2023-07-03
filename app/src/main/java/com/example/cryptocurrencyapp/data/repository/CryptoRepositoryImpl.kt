package com.example.cryptocurrencyapp.data.repository

import android.util.Log
import com.example.cryptocurrencyapp.utils.Resource
import com.example.cryptocurrencyapp.data.local.LocalCryptoData
import com.example.cryptocurrencyapp.data.model.*
import com.example.cryptocurrencyapp.data.remote.RemoteCryptoData
import com.example.cryptocurrencyapp.utils.Constants.FIREBASE_FAVOURITES
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CryptoRepositoryImpl
@Inject
constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth,
    private val remoteCryptoData: RemoteCryptoData,
    private val localCryptoData: LocalCryptoData,
    private val dispatcher: CoroutineDispatcher
) : CryptoRepository {

    override fun getAllCryptos(): Flow<Resource<List<CryptoModel>>> = flow {
        emit(Resource.Loading)

        val result = try {
            remoteCryptoData.getCryptoListFromAPI()
        } catch (throwable: Throwable) {
            emit(Resource.Failure(throwable.message ?: throwable.localizedMessage))
            null
        }
        if (result.isNullOrEmpty()) {
            val localResult = localCryptoData.getAllCryptosFromDB()
            emit(Resource.Success(CryptoDBEntityMapper().toCryptoList(localResult)))
        }else{
            localCryptoData.insertCryptoList(CryptoDBEntityMapper().toEntityList(result))
            emit(Resource.Success(result))
        }

    }

    override fun getCryptoByID(cryptoId: String): Flow<Resource<CryptoDetailResponse>> = flow {
        emit(Resource.Loading)
        try {
            val result = remoteCryptoData.getCoinByIdFromAPI(cryptoId)
            emit(Resource.Success(result))
        } catch (throwable: Throwable) {
            emit(Resource.Failure(throwable.message ?: throwable.localizedMessage))
        }
    }

    override fun getCryptosCurrentPriceByID(delay: Long, cryptoId: String): Flow<Resource<Double>> {
        return channelFlow {
            while (!isClosedForSend) {
                send(Resource.Loading)
                try {
                    val result = remoteCryptoData.getCoinByIdFromAPI(cryptoId)
                    Log.e("currentPrice:", result.market_data.currentPrice.usd.toString())
                    send(Resource.Success(result.market_data.currentPrice.usd))
                } catch (throwable: Throwable) {
                    send(Resource.Failure(throwable.message ?: throwable.localizedMessage))
                }
                delay(delay)
            }
        }.flowOn(dispatcher)
    }

    override fun addCryptoToFavorites(cryptoDetailResponse: CryptoDetailResponse) = flow {

        emit(Resource.Loading)

        val currentUser = firebaseAuth.currentUser
        currentUser?.let { user ->
            val firestoreFav = firestore.collection(FIREBASE_FAVOURITES).document(user.uid)
                .collection("cryptos")
                .document(CryptoDetailToCryptoMapper().mapToCryptoModel(cryptoDetailResponse).name)
                .set(CryptoDetailToCryptoMapper().mapToCryptoModel(cryptoDetailResponse))

            firestoreFav.await()

            emit(Resource.Success(firestoreFav))
        }


    }.catch {
        emit(Resource.Failure(it.message ?: it.localizedMessage))
    }

    override fun getAllFavoritesFromFireStore() = flow {

        emit(Resource.Loading)

        val currentUser = firebaseAuth.currentUser
        currentUser?.let { user ->
            val snapshot = firestore.collection(FIREBASE_FAVOURITES).document(user.uid)
                .collection("cryptos").get()
                .await()

            val data = snapshot.toObjects(FavoriteModel::class.java)

            emit(Resource.Success(data))
        }


    }.catch {
        emit(Resource.Failure(it.message ?: it.localizedMessage))
    }

    override fun deleteFromFavorites(cryptoModel: FavoriteModel) = flow {

        emit(Resource.Loading)

        val currentUser = firebaseAuth.currentUser
        currentUser?.let { user ->
            val firestoreFav = firestore.collection(FIREBASE_FAVOURITES).document(user.uid)
                .collection("cryptos").document(cryptoModel.name).delete()

            firestoreFav.await()


            emit(Resource.Success(firestoreFav))
        }

    }.catch {
        emit(Resource.Failure(it.message ?: it.localizedMessage))
    }

    override fun getSearchedCryptoFromDB(searchPattern: String) = flow {
        emit(Resource.Loading)
        try {
            val result = localCryptoData.getSearchedCryptoFromDB(searchPattern)
            emit(Resource.Success(CryptoDBEntityMapper().toCryptoList(result)))
        } catch (throwable: Throwable) {
            emit(Resource.Failure(throwable.message ?: throwable.localizedMessage))
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }


}