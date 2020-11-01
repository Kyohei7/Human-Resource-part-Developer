package com.example.humanresource2.login.post_profile


import com.google.gson.annotations.SerializedName

data class PostProfileResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)