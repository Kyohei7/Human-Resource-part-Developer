package com.example.humanresource2.service

import com.example.humanresource2.profile.ProfileResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ProfileApiService {

    @GET("developer/{id}")
    suspend fun getProfileDevById(@Path("id") id: Int?) : ProfileResponse

}