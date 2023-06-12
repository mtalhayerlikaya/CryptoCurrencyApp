package com.example.cryptocurrencyapp.view.loginregister

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cryptocurrencyapp.databinding.ActivityLoginRegisterBinding



class LoginRegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }


}