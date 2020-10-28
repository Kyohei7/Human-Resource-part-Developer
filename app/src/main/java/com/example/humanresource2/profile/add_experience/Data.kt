package com.example.humanresource2.profile.add_experience


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("company")
    val company: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("id_developer")
    val idDeveloper: String,
    @SerializedName("link")
    val link: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("photo")
    val photo: String,
    @SerializedName("repository")
    val repository: String,
    @SerializedName("type")
    val type: String
)