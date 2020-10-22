package com.example.humanresource2.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.humanresource2.helper.PreferencesHelper
import com.example.humanresource2.service.AuthApiService
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class RegisterViewModel: ViewModel(), CoroutineScope {

    val isRegisterLiveData = MutableLiveData<Boolean>()

    private lateinit var service: AuthApiService
    private lateinit var sharedPreferences: PreferencesHelper

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setSharePreference(sharedPreferences: PreferencesHelper) {
        this.sharedPreferences = sharedPreferences
    }

    fun setRegisterService(service: AuthApiService) {
        this.service = service
    }


    fun callApiRegister(username: String, email: String, password: String, role: String) {
        launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service?.registerRequest(username, email, password, role)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        isRegisterLiveData.value = false
                    }
                }
            }
            if (response is RegisterResponse) {
                if (response.message == "Success Register Account!") {
                    isRegisterLiveData.value = true
                } else {
                    isRegisterLiveData.value = false
                }
            }
        }
    }
}