package com.example.cryptocurrencyapp.view.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocurrencyapp.utils.Resource
import com.example.cryptocurrencyapp.data.model.FavoriteModel
import com.example.cryptocurrencyapp.data.repository.CryptoRepository
import com.google.android.gms.tasks.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel
@Inject
constructor(private val cryptoRepository: CryptoRepository) : ViewModel() {

    private val _firebaseAllFavCryptosFlow = MutableSharedFlow<Resource<List<FavoriteModel>>>()
    val firebaseAllFavCryptosFlow: SharedFlow<Resource<List<FavoriteModel>>> = _firebaseAllFavCryptosFlow

    private val _deleteCryptoFlow = MutableSharedFlow<Resource<Task<Void>>>()
    val deleteCryptoFlow: SharedFlow<Resource<Task<Void>>> = _deleteCryptoFlow

    fun getAllFavoriteCryptos() = viewModelScope.launch {
        cryptoRepository.getAllFavoritesFromFireStore().collect() {
            _firebaseAllFavCryptosFlow.emit(it)
        }
    }

    fun deleteCryptoFromFavorites(cryptoModel: FavoriteModel) = viewModelScope.launch {
        cryptoRepository.deleteFromFavorites(cryptoModel).collect() {
            _deleteCryptoFlow.emit(it)
        }
    }


    fun logout() {
        cryptoRepository.logout()
    }

}