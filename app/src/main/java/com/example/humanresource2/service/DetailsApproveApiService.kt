package com.example.humanresource2.service

import com.example.humanresource2.approve.detail.DetailsApproveResponse
import okhttp3.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailsApproveApiService {

    @GET("hire/{id}")
    fun getHireByIDHire(@Path("id")id:String?): retrofit2.Call<DetailsApproveResponse>


}