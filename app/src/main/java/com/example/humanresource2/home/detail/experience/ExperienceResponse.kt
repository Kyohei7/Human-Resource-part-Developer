package com.example.humanresource2.home.detail.experience


import com.google.gson.annotations.SerializedName

data class ExperienceResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)