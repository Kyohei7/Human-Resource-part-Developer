package com.example.humanresource2.profile

import com.google.gson.annotations.SerializedName

class ProfileResponse(val success: Boolean?, val message: String?, val data: Profile) {

    data class Profile(
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
            @SerializedName("id_user")
            val idUser: String,
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
            val status: String
    )
}