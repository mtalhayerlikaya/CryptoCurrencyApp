package com.example.cryptocurrencyapp.view.loginregister

import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocurrencyapp.utils.Resource
import com.example.cryptocurrencyapp.data.repository.AuthRepository
import com.example.cryptocurrencyapp.utils.LoginResult
import com.example.cryptocurrencyapp.utils.SignUpResult
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel
@Inject
constructor(private val repository: AuthRepository) : ViewModel() {

    private val _loginFlow = MutableSharedFlow<Resource<AuthResult>>()
    val loginFlow: SharedFlow<Resource<AuthResult>> = _loginFlow

    private val _signupFlow = MutableSharedFlow<Resource<AuthResult>>()
    val signupFlow: SharedFlow<Resource<AuthResult>> = _signupFlow

    val currentUser: FirebaseUser?
        get() = repository.currentUser


    fun loginUser(email: String, password: String) = viewModelScope.launch {
        repository.login(email, password).collect() {
            _loginFlow.emit(it)
        }

    }

    fun signupUser(name: String, email: String, password: String) = viewModelScope.launch {
        repository.signUp(name, email, password).collect() {
            _signupFlow.emit(it)
        }
    }

    fun canUserLogin(email: String, password: String): LoginResult {
        if (!isValidEmail(email)) {
            return LoginResult.INVALID_EMAIL
        } else if (!isValidPassword(password)) {
            return LoginResult.INVALID_PASSWORD
        }
        return LoginResult.OK
    }

    fun canUserSingUp(email: String, password: String): SignUpResult {
        if (!isValidEmail(email)) {
            return SignUpResult.INVALID_EMAIL
        } else if (!isValidPassword(password)) {
            return SignUpResult.INVALID_PASSWORD
        }
        return SignUpResult.OK
    }

    fun isValidEmail(email: CharSequence): Boolean {
        return if (TextUtils.isEmpty(email)) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }

    fun isValidPassword(password: CharSequence): Boolean {
        if (password.isNotEmpty() && password.length > 5) {
            return true
        }
        return false
    }

}