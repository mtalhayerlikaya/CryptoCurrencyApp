package com.example.cryptocurrencyapp.view.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocurrencyapp.utils.Resource
import com.example.cryptocurrencyapp.data.model.CryptoEntity
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

    private val _searchCryptoFlow= MutableSharedFlow<Resource<List<CryptoModel>>>()
    val searchCryptoFlow: SharedFlow<Resource<List<CryptoModel>>> = _searchCryptoFlow

    val loggedInUser = cryptoRepository.getCurrentUserFromFirabase()

    fun getCryptos() = viewModelScope.launch {
        cryptoRepository.getAllCryptos().collect() {
            _allCryptos.emit(it)
        }
    }

    fun searchCrypto(searchPattern:String)=viewModelScope.launch{
        cryptoRepository.getSearchedCryptoFromDB(searchPattern).collect() {
            _searchCryptoFlow.emit(it)
        }
    }


    fun logout(){
        cryptoRepository.logout()
    }



}