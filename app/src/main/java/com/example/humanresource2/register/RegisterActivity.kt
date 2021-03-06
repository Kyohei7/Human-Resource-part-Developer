package com.example.humanresource2.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.humanresource2.R
import com.example.humanresource2.databinding.ActivityRegisterBinding
import com.example.humanresource2.helper.Constant
import com.example.humanresource2.helper.PreferencesHelper
import com.example.humanresource2.login.LoginActivity
import com.example.humanresource2.remote.ApiClient
import com.example.humanresource2.service.AuthApiService

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var sharePreferencesHelper: PreferencesHelper
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =DataBindingUtil.setContentView(this, R.layout.activity_register)
        sharePreferencesHelper = PreferencesHelper(this)

        val service = ApiClient.getApiClient(this)?.create(AuthApiService::class.java)
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        viewModel.setSharePreference(sharePreferencesHelper)

        if (service != null) {
            viewModel.setRegisterService(service)
        }

        binding.btnRegister.setOnClickListener {
            viewModel.callApiRegister(
                binding.etUsername.text.toString(),
                binding.etRegisterEmail.text.toString(),
                binding.etRegisterPassword.text.toString(),
                binding.etRegisterRole.text.toString()
            )
        }

        subscribeLiveData()

    }

    private fun subscribeLiveData() {
        viewModel.isRegisterLiveData.observe(this, Observer {
            Log.d("Register", "$it")

            if (it) {
                sharePreferencesHelper.putBoolean(Constant.PREFERENCE_IS_REGISTER, true)
                Toast.makeText(this, "Register Succcess", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Register Failed!", Toast.LENGTH_SHORT).show()
            }
        })
    }
}