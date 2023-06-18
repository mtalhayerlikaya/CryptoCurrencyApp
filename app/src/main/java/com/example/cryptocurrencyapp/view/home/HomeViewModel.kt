package com.example.cryptocurrencyapp.view.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocurrencyapp.Resource
import com.example.cryptocurrencyapp.data.model.CryptoDetailResponse
import com.example.cryptocurrencyapp.data.model.CryptoModel
import com.example.cryptocurrencyapp.data.repository.CryptoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject constructor(private val cryptoRepository: CryptoRepository) : ViewModel() {

    private val _allCryptos = MutableSharedFlow<Resource<List<CryptoModel>>>()
    val allCryptos: SharedFlow<Resource<List<CryptoModel>>> = _allCryptos




    fun getCryptos() = viewModelScope.launch {
        cryptoRepository.getAllCryptos().collect() {
            _allCryptos.emit(it)
        }
    }
    fun logout(){
        cryptoRepository.logout()
    }
}