package com.example.humanresource2.approve


import com.google.gson.annotations.SerializedName

data class ApproveResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)