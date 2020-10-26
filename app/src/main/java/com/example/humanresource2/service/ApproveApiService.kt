package com.example.humanresource2.service

import com.example.humanresource2.approve.ApproveResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApproveApiService {

    @GET("hire/hireId/{id}")
    fun getHireByID(@Path("id") id: String?): Call<ApproveResponse>

}