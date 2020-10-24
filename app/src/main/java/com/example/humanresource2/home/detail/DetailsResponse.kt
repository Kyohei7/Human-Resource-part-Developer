package com.example.humanresource2.home.detail

import com.example.humanresource2.home.HomeResponse
import com.google.gson.annotations.SerializedName

class DetailsResponse(val success: String?, val message: String?, val data: DataResult?) {

    data class DataResult(
        @SerializedName("createAt")
        val createAt: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("email")
        val email: String,
        @SerializedName("github")
        val github: String,
        @SerializedName("gitlab")
        val gitlab: String,
        @SerializedName("id_developer")
        val idDeveloper: Int,
        @SerializedName("instagram")
        val instagram: String,
        @SerializedName("job")
        val job: String,
        @SerializedName("location")
        val location: String,
        @SerializedName("name_developer")
        val nameDeveloper: String,
        @SerializedName("photo")
        val photo: String,
        @SerializedName("skill")
        val skill: String,
        @SerializedName("status")
        val status: String,
        @SerializedName("updateAt")
        val updateAt: String
    )
}