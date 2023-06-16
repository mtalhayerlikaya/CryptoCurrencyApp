package com.example.cryptocurrencyapp.view.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocurrencyapp.Resource
import com.example.cryptocurrencyapp.data.model.CryptoDetailResponse
import com.example.cryptocurrencyapp.data.repository.CryptoRepository
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

    fun getCryptoDetail(cryptoID: String) = viewModelScope.launch {
        cryptoRepository.getCryptoByID(cryptoID).collect() {
            _cryptoDetail.emit(it)
        }
    }
}