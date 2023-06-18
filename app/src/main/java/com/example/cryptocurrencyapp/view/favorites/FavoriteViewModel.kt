package com.example.cryptocurrencyapp.view.favorites

import androidx.lifecycle.ViewModel
import com.example.cryptocurrencyapp.data.repository.CryptoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel
@Inject
constructor(private val cryptoRepository: CryptoRepository) : ViewModel() {

    fun logout() {
        cryptoRepository.logout()
    }

}