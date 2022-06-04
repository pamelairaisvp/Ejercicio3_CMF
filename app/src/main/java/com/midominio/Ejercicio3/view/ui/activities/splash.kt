package com.midominio.Ejercicio3.view.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.midominio.Ejercicio3.databinding.ActivitySplashBinding
import kotlin.concurrent.thread

class splash : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        thread{
            Thread.sleep(7000)
            startActivity(Intent(this@splash, MainActivity::class.java))
            finish()
        }

    }
}