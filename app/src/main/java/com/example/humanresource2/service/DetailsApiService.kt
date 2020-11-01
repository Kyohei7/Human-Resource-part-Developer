package com.example.humanresource2.service

import com.example.humanresource2.home.HomeResponse
import com.example.humanresource2.home.detail.DetailsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailsApiService {

    @GET("developer/idDev/{id}")
    suspend fun getAllDevelopersById(@Path("id") id: Int): DetailsResponse

}