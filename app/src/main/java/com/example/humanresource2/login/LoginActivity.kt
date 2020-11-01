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
import com.example.humanresource2.login.post_profile.PostProfileActivity
import com.example.humanresource2.remote.ApiClient
import com.example.humanresource2.service.AuthApiService

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var sharePreferencesHelper: PreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
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
        viewModel.isResponseLogin.observe(this, Observer {
            if (it) {

            }
            else {
                Toast.makeText(this, "FAILED", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.isLoginLiveData.observe(this, Observer {
            if (it) {
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                viewModel.isRegisterLiveData.observe(this, Observer {
                    if (it) {
                        Toast.makeText(this, "To Form", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this, PostProfileActivity::class.java))
                    }
                    else {
                        startActivity(Intent(this, HomeActivity::class.java))
                        finish()
                    }
                })
            } else {
                Toast.makeText(this, "You Don't Have Access", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.isToastLogin.observe(this, Observer {
            if (it) {

            } else {
                Toast.makeText(this, "Fill The Blank!", Toast.LENGTH_SHORT).show()
            }
        })
    }
}