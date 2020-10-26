package com.example.humanresource2.approve


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("description")
    val description: String,
    @SerializedName("id_hire")
    val idHire: String,
    @SerializedName("name_company")
    val nameCompany: String,
    @SerializedName("name_project")
    val nameProject: String,
    @SerializedName("photo")
    val photo: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("status")
    val status: String
)