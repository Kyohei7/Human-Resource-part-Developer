package com.example.humanresource2.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.humanresource2.home.HomeModel
import com.example.humanresource2.home.HomeResponse
import com.example.humanresource2.service.HomeApiService
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SearchViewModel: ViewModel(), CoroutineScope {

    private lateinit var service: HomeApiService
    val DevLiveData = MutableLiveData<List<HomeModel>>()
    val isLoadingProgressBarliveData = MutableLiveData<Boolean>()
    val toastLiveData = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setDevService(service: HomeApiService) {
        this.service = service
    }

    fun callDevApi() {
        launch {
            isLoadingProgressBarliveData.value = true
            val response = withContext(Job() + Dispatchers.IO) {
                try {
                    service?.getAllDevelopers()
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        toastLiveData.value = true
                    }
                }
            }
            if (response is HomeResponse) {
                val list = response.data?.map {
                    HomeModel(
                            it.id.orEmpty(),
                            it.photo.orEmpty(),
                            it.name.orEmpty(),
                            it.job.orEmpty(),
                            it.location.orEmpty(),
                            it.status.orEmpty(),
                            it.description.orEmpty(),
                            it.skill.orEmpty(),
                            it.email.orEmpty(),
                            it.instagram.orEmpty(),
                            it.github.orEmpty(),
                            it.gitlab.orEmpty(),
                            it.createAt.orEmpty(),
                            it.updateAt.orEmpty()
                    )
                }
                DevLiveData.value = list
            } else {
                isLoadingProgressBarliveData.value = false
                toastLiveData.value = true
            }
            isLoadingProgressBarliveData.value = false
        }
    }
}