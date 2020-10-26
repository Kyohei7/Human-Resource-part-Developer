package com.example.humanresource2.profile


import com.google.gson.annotations.SerializedName

data class dc(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)