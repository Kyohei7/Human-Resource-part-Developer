package com.example.humanresource2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.databinding.DataBindingUtil
import com.example.humanresource2.databinding.ActivityMainBinding
import com.example.humanresource2.helper.Constant
import com.example.humanresource2.helper.PreferencesHelper
import com.example.humanresource2.home.HomeActivity
import com.example.humanresource2.login.LoginActivity
import com.example.humanresource2.register.RegisterActivity

class MainActivity : AppCompatActivity() {

    lateinit var handler: Handler
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharePreferences: PreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        supportActionBar?.hide()

        sharePreferences = PreferencesHelper(applicationContext)
        val token = sharePreferences.getString(Constant.PREFERENCES_IS_TOKEN)

        handler = Handler()
        handler.postDelayed({
            if (token != null) {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                startActivity(Intent(this, OnboardActivity::class.java))
            }
        }, 3000)
    }
}