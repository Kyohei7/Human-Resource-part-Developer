package com.example.humanresource2.home.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.humanresource2.service.DetailsApiService
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class DetailsViewModel: ViewModel(), CoroutineScope {

    private lateinit var service: DetailsApiService
    val isProgressLiveData = MutableLiveData<Boolean>()
    val detailsLiveData = MutableLiveData<DetailsResponse>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setDetailsService(service: DetailsApiService) {
        this.service = service
    }

    fun detailsCallApi(id: Int) {
        launch {
            isProgressLiveData.value = true
            val response = withContext(Job() + Dispatchers.IO) {
                try {
                    service?.getAllDevelopersById(id)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
            Log.d("checkId", (response is DetailsResponse).toString())
            if (response is DetailsResponse) {
                detailsLiveData.value = response
            } else if (response is Throwable) {
                Log.e("error", response.message ?: "Error")
            }
            isProgressLiveData.value = false
        }
    }
}