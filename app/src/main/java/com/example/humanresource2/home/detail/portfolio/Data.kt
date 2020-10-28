package com.example.humanresource2.home.detail.portfolio


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("company")
    val company: String,
    @SerializedName("createAt")
    val createAt: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("id_developer")
    val idDeveloper: Int,
    @SerializedName("id_portfolio")
    val idPortfolio: String,
    @SerializedName("link")
    val link: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("photo")
    val photo: String,
    @SerializedName("repository")
    val repository: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("updateAt")
    val updateAt: String
)