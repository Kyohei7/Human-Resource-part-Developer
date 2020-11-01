package com.example.humanresource2.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.humanresource2.helper.Constant
import com.example.humanresource2.helper.PreferencesHelper
import com.example.humanresource2.service.AuthApiService
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class LoginViewModel :ViewModel(), CoroutineScope {


    val isLoginLiveData = MutableLiveData<Boolean>()
    val isRegisterLiveData = MutableLiveData<Boolean>()
    val isToastLogin = MutableLiveData<Boolean>()
    val isResponseLogin = MutableLiveData<Boolean>()

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

                }
            }
            if (response is LoginResponse) {
                isResponseLogin.value = true
                if (email.isNotEmpty() && password.isNotEmpty()){
                    isToastLogin.value = true
                    if (response.data?.role == "developer") {
                        response.data?.token?.let { sharedPreferences.putString(Constant.PREFERENCES_IS_TOKEN, it) }
                        val a= sharedPreferences.putBoolean(Constant.PREFERENCES_IS_LOGIN, true)
                        Log.d("sharedpref login", "$a")
                        response.data.id?.let { sharedPreferences.putString(Constant.PREFERENCES_ID, it) }
                        response.data.name?.let { sharedPreferences.putString(Constant.PREFERENCES_IS_USERNAME, it) }
                        val b = response.data.email?.let { sharedPreferences.putString(Constant.PREFERENCE_IS_EMAIL, it) }
                        Log.d("email", "$b")
                        val reg = sharedPreferences.getBoolean(Constant.PREFERENCE_IS_REGISTER)
                        isLoginLiveData.value = true
                        isRegisterLiveData.value = reg
                    } else {
                        isLoginLiveData.value = false
                    }
                }
                else {
                    isToastLogin.value = false
                }
            }
            else {
                isResponseLogin.value = false
            }
        }

    }
}