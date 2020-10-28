package com.example.humanresource2.profile.add_experience


import com.google.gson.annotations.SerializedName

data class AddExperienceResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)