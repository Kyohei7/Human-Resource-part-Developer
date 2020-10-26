package com.example.humanresource2.approve.detail


import com.google.gson.annotations.SerializedName

data class DetailsApproveResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)