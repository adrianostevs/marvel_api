package com.example.marvelapi.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.example.marvelapi.base.BaseActivity
import com.example.marvelapi.databinding.ActivitySplashScreenBinding
import com.example.marvelapi.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreen : BaseActivity<ActivitySplashScreenBinding>() {

    override fun onViewBinding() = ActivitySplashScreenBinding.inflate(layoutInflater)

    override fun onCreated(savedInstanceState: Bundle?) {
        Handler(mainLooper).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
        }, 3000)
    }
}