package com.example.humanresource2.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.humanresource2.service.HomeApiService
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class HomeViewModel: ViewModel(), CoroutineScope {

    private lateinit var service: HomeApiService
    val isLoadingLiveData = MutableLiveData<Boolean>()
    val listLiveData = MutableLiveData<List<HomeModel>>()

        override val coroutineContext: CoroutineContext
            get() = Job() + Dispatchers.Main

    fun setHomeService(service: HomeApiService) {
        this.service = service
    }

    fun callApiHome() {
        launch {
            isLoadingLiveData.value = true
            val response = withContext(Dispatchers.IO) {
                try {
                    service.getAllDevelopers()
                } catch (e: Throwable) {
                    e.printStackTrace()
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
                } ?: listOf()
                listLiveData.value = list
            }
                isLoadingLiveData.value = false
        }
    }
}