package com.example.humanresource2.profile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.humanresource2.service.ProfileApiService
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ProfileViewModel: ViewModel(), CoroutineScope {

    private lateinit var service: ProfileApiService
    val profileLiveData = MutableLiveData<ProfileResponse>()
    val isProgressLiveData = MutableLiveData<Boolean>()
    val toastLiveData = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setProfileService(service: ProfileApiService) {
        this.service = service
    }

    fun getProfileDevApi(id: Int) {
        launch {
            Log.d("cek id", "$id")
            isProgressLiveData.value = true
            val response = withContext(Dispatchers.IO) {
                try {
                    service.getProfileDevById(id)
                } catch (e: Throwable) {

                }
            }
            if (response is ProfileResponse) {
                Log.d("android1", "$response")
                profileLiveData.value = response
            } else {
                toastLiveData.value = false
            }
            isProgressLiveData.value = false
        }
    }
}