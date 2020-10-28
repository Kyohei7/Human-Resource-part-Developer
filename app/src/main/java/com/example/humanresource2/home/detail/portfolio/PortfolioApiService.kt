package com.example.humanresource2.home.detail.portfolio

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PortfolioApiService {

    @GET("portfolio/{id}")
    fun getPortofolioByID(@Path("id")id:String?) : Call<PortfolioResponse>

}