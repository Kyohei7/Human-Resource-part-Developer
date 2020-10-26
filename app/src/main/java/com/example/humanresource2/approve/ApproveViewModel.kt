package com.example.humanresource2.approve

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.humanresource2.helper.Constant
import com.example.humanresource2.helper.PreferencesHelper
import com.example.humanresource2.service.ApproveApiService
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class ApproveViewModel: ViewModel() {

    val isApproveResponse = MutableLiveData<List<ApproveModel>>()
    private lateinit var service: ApproveApiService
    private lateinit var sharePref: PreferencesHelper

    fun setSharedPreferences(sharedpref: PreferencesHelper) {
        this.sharePref = sharedpref
    }

    fun setServiceConfirm(service: ApproveApiService){
        this.service = service
    }

    fun getHireByID() {
        val id = sharePref.getString(Constant.PREFERENCES_IS_ID_DEV)
        service.getHireByID(id).enqueue(object : retrofit2.Callback<ApproveResponse> {
            override fun onFailure(call: Call<ApproveResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<ApproveResponse>,
                response: Response<ApproveResponse>
            ) {
                val list = response.body()?.data?.map {
                    ApproveModel(
                        it.idHire.orEmpty(),
                        it.nameCompany.orEmpty(),
                        it.nameProject.orEmpty(),
                        it.description.orEmpty(),
                        it.photo.orEmpty(),
                        it.status.orEmpty()
                    )
                } ?: listOf()
                isApproveResponse.value = list
            }
        })
    }
}


