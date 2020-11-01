package com.example.humanresource2.login.post_profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.humanresource2.R
import com.example.humanresource2.databinding.ActivityPostProfileBinding

class PostProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post_profile)
    }
}