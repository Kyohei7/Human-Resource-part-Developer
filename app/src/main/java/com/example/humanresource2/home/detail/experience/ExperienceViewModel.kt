package com.example.humanresource2.home.detail.experience

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.humanresource2.helper.Constant
import com.example.humanresource2.helper.PreferencesHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExperienceViewModel: ViewModel() {

    val isResponseExperience = MutableLiveData<List<ExperienceModel>>()
    private lateinit var service: ExperienceApiService
    private lateinit var sharePref: PreferencesHelper

    fun setSharePreferences(sharePref: PreferencesHelper ) {
        this.sharePref = sharePref
    }

    fun setServiceExperience(service: ExperienceApiService) {
        this.service = service
    }

    fun callApiExperience() {
        val idDev = sharePref.getString(Constant.PREFERENCES_IS_ID_DEV)
        service.getExperienceByID(idDev).enqueue(object : Callback<ExperienceResponse> {
            override fun onFailure(call: Call<ExperienceResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<ExperienceResponse>, response: Response<ExperienceResponse>) {
                val list = response.body()?.data?.map {
                    ExperienceModel(
                            it.idExperience.orEmpty(),
                            it.position.orEmpty(),
                            it.company.orEmpty(),
                            it.duration.orEmpty(),
                            it.description.orEmpty()
                    )
                } ?: listOf()
                isResponseExperience.value = list
            }

        })
    }

}