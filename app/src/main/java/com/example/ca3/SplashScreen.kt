package com.example.ca3

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)



        val sharedPref = getSharedPreferences("saved", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPref.getBoolean("isLoggedIn", false)

        Handler(Looper.getMainLooper()).postDelayed(
            {val intent = if(isLoggedIn) {
                val userId = sharedPref.getString("userId", "")
                val password = sharedPref.getString("password", "")
                Intent(this, MainActivity::class.java).apply {
                    putExtra("userId", userId)
                    putExtra("password", password)
                }
            }
            else {
                // User is not logged in, redirect to Register
                Intent(this, Login ::class.java)
            }

                startActivity(intent)
                finish()
            }, 3000
        )

    }
}