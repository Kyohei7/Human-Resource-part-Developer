package com.example.humanresource2.home.detail.experience


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("company")
    val company: String,
    @SerializedName("createAt")
    val createAt: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("duration")
    val duration: String,
    @SerializedName("id_developer")
    val idDeveloper: Int,
    @SerializedName("id_experience")
    val idExperience: String,
    @SerializedName("position")
    val position: String,
    @SerializedName("updateAt")
    val updateAt: String
)