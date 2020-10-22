package com.example.humanresource2.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.humanresource2.R
import com.example.humanresource2.databinding.ActivityLoginBinding
import com.example.humanresource2.helper.Constant
import com.example.humanresource2.helper.PreferencesHelper
import com.example.humanresource2.home.HomeActivity
import com.example.humanresource2.remote.ApiClient
import com.example.humanresource2.service.AuthApiService

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var sharePreferencesHelper: PreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        sharePreferencesHelper = PreferencesHelper(this)

        val service = ApiClient.getApiClient(this)?.create(AuthApiService::class.java)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        viewModel.setSharePreference(sharePreferencesHelper)

        if (service != null) {
            viewModel.setLoginService(service)
        }

        binding.btnLogin.setOnClickListener {
            viewModel.callApiLogin(
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            )
        }

        subscribeLiveData()
    }


    private fun moveIntent() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    override fun onStart() {
        super.onStart()
        if (sharePreferencesHelper.getBoolean(Constant.PREFERENCES_IS_LOGIN)) {
            moveIntent()
        }
    }

    private fun subscribeLiveData() {
        viewModel.isLoginLiveData.observe(this, Observer {
            Log.d("developer", "$it")

            if (it) {
                Toast.makeText(this, "Login Succcess", Toast.LENGTH_SHORT).show()
                moveIntent()
                finish()
            } else {
                Toast.makeText(this, "Login Failed!", Toast.LENGTH_SHORT).show()
            }
        })
    }
}