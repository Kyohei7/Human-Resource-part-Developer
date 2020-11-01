package com.example.humanresource2.approve.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.humanresource2.helper.Constant
import com.example.humanresource2.helper.PreferencesHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsApproveViewModel: ViewModel() {

    val isDetailApproveResponse  = MutableLiveData<DetailsApproveResponse>()
    val isApproveResponseReject  = MutableLiveData<Boolean>()
    val isApproveResponseApprove = MutableLiveData<Boolean>()

    private lateinit var service: DetailsApproveApiService
    private lateinit var sharePref: PreferencesHelper

    fun setSharedPreference(sharePref : PreferencesHelper) {
        this.sharePref = sharePref
    }

    fun setServiceStatus(service: DetailsApproveApiService) {
        this.service = service
    }

    fun callApiDetails() {
        val id = sharePref.getString(Constant.PREFERENCE_IS_ID_HIRE)
        service.getHireByIDHire(id).enqueue(object : Callback<DetailsApproveResponse> {
            override fun onFailure(call: Call<DetailsApproveResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<DetailsApproveResponse>,
                response: Response<DetailsApproveResponse>
            ) {
                Log.d("Response detail status", "${response.body()}")
                isDetailApproveResponse.value = response.body()
            }

        })
    }

    fun statusReject() {
        val id = sharePref.getString(Constant.PREFERENCE_IS_ID_HIRE)
        service.getUpdate(id, "Reject").enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {

            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                isApproveResponseReject.value = true
            }

        })
    }

    fun statusAccept() {
        val id = sharePref.getString(Constant.PREFERENCE_IS_ID_HIRE)
        service.getUpdate(id, "Approve").enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {

            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                isApproveResponseApprove.value = true
            }

        })
    }
}