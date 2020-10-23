package com.example.humanresource2.service

import com.example.humanresource2.home.HomeResponse
import retrofit2.http.GET

interface HomeApiService {

    @GET("developer")
    suspend fun getAllDevelopers(): HomeResponse
}