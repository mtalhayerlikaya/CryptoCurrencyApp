package com.example.cryptocurrencyapp.view.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocurrencyapp.utils.Resource
import com.example.cryptocurrencyapp.data.model.CryptoDetailResponse
import com.example.cryptocurrencyapp.data.repository.CryptoRepository
import com.google.android.gms.tasks.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel
@Inject constructor(private val cryptoRepository: CryptoRepository) : ViewModel() {

    private val _cryptoDetail = MutableSharedFlow<Resource<CryptoDetailResponse>>()
    val cryptoDetail: SharedFlow<Resource<CryptoDetailResponse>> = _cryptoDetail

    private val _detailCurrentPrice = MutableSharedFlow<Resource<Double>>()
    val detailCurrentPrice: SharedFlow<Resource<Double>> = _detailCurrentPrice

    private val _firebaseFav = MutableSharedFlow<Resource<Task<Void>>>()
    val firebaseFav: SharedFlow<Resource<Task<Void>>> = _firebaseFav


    fun getCryptoDetail(cryptoID: String) = viewModelScope.launch {
        cryptoRepository.getCryptoByID(cryptoID).collect() {
            _cryptoDetail.emit(it)
        }
    }

    fun getCryptosCurrentPriceByID(delay: Long, cryptoID: String) = viewModelScope.launch {
        cryptoRepository.getCryptosCurrentPriceByID(delay, cryptoID).collect() {
            _detailCurrentPrice.emit(it)
        }
    }

    fun addCryptoToFavorities(cryptoDetailResponse: CryptoDetailResponse) = viewModelScope.launch {
        cryptoRepository.addCryptoToFavorites(cryptoDetailResponse).collect() {
            _firebaseFav.emit(it)
        }
    }

}