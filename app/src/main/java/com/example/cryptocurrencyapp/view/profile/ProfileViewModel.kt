package com.example.cryptocurrencyapp.view.profile

import androidx.lifecycle.ViewModel
import com.example.cryptocurrencyapp.data.repository.CryptoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel
@Inject
constructor(private val cryptoRepository: CryptoRepository):ViewModel() {

    fun logout(){
        cryptoRepository.logout()
    }
}