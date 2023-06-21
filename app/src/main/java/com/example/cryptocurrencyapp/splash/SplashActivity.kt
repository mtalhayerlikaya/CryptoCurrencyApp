package com.example.cryptocurrencyapp.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.cryptocurrencyapp.databinding.ActivitySplashBinding
import com.example.cryptocurrencyapp.view.loginregister.LoginRegisterActivity

class SplashActivity : AppCompatActivity() {


    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Handler(Looper.myLooper()!!).postDelayed({
            startActivity(Intent(this, LoginRegisterActivity::class.java))
            finish()
        }, 4000)


    }


}