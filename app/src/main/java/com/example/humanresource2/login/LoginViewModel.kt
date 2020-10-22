package com.example.humanresource2.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.humanresource2.helper.Constant
import com.example.humanresource2.helper.PreferencesHelper
import com.example.humanresource2.service.AuthApiService
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class LoginViewModel :ViewModel(), CoroutineScope {


    val isLoginLiveData = MutableLiveData<Boolean>()
    private lateinit var service: AuthApiService
    private lateinit var sharedPreferences: PreferencesHelper

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setSharePreference(sharedPreferences: PreferencesHelper) {
        this.sharedPreferences = sharedPreferences
    }

    fun setLoginService(service: AuthApiService) {
        this.service = service
    }

    fun callApiLogin(email: String, password: String) {
        launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service?.loginRequest(email, password)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        isLoginLiveData.value = false
                    }
                }
            }
            if (response is LoginResponse) {
                if (response.data?.role == "developer") {
                    sharedPreferences.putString(Constant.PREFERENCES_IS_TOKEN, response.data.token)
                    sharedPreferences.putBoolean(Constant.PREFERENCES_IS_LOGIN, true)
                    sharedPreferences.putString(Constant.PREFERENCES_ID, response.data.id)
                    sharedPreferences.putString(Constant.PREFERENCES_IS_USERNAME, response.data.name)
                    isLoginLiveData.value = true
                } else {
                    isLoginLiveData.value = false
                }
            }
        }
    }
}