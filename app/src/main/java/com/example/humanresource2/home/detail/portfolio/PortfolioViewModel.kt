package com.example.humanresource2.home.detail.portfolio

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.humanresource2.helper.Constant
import com.example.humanresource2.helper.PreferencesHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PortfolioViewModel: ViewModel() {

    val isResponsePortfolio = MutableLiveData<List<PortfolioModel>>()
    private lateinit var service: PortfolioApiService
    private lateinit var sharePref: PreferencesHelper

    fun setServicePortfolio(service: PortfolioApiService) {
        this.service = service
    }

    fun setSharePreference(sharePref: PreferencesHelper) {
        this.sharePref = sharePref
    }

    fun callApiPortfolio() {
        val idDev = sharePref.getString(Constant.PREFERENCES_IS_ID_DEV)
        service.getPortofolioByID(idDev).enqueue(object : Callback<PortfolioResponse> {
            override fun onFailure(call: Call<PortfolioResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<PortfolioResponse>, response: Response<PortfolioResponse>) {
                val list = response.body()?.data?.map {
                    PortfolioModel(
                            it.idPortfolio.orEmpty(),
                            it.photo.orEmpty()
                    )
                } ?: listOf()
                isResponsePortfolio.value = list
            }

        })
    }
}