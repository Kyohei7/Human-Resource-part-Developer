package com.example.humanresource2.approve.detail

import com.example.humanresource2.approve.detail.DetailsApproveResponse
import okhttp3.Call
import retrofit2.http.*

interface DetailsApproveApiService {

    @GET("hire/{id}")
    fun getHireByIDHire(@Path("id")id:String?): retrofit2.Call<DetailsApproveResponse>



    @FormUrlEncoded
    @PATCH("hire/{id}")
    fun getUpdate(@Path("id")id:String?,
                  @Field("status") status:String): retrofit2.Call<Void>

}