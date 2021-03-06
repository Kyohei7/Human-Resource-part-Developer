package com.example.humanresource2.home.detail.portfolio


import com.google.gson.annotations.SerializedName

data class PortfolioResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)