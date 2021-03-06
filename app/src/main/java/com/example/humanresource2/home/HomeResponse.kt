package com.example.humanresource2.home

import com.google.gson.annotations.SerializedName

class HomeResponse (val success: String?, val message: String?, val data: List<Developer>) {

    data class Developer(

            @SerializedName("id_developer")     val id:             String?,
            @SerializedName("photo")            val photo:          String?,
            @SerializedName("name_developer")   val name:           String?,
            @SerializedName("job")              val job:            String?,
            @SerializedName("location")         val location:       String?,
            @SerializedName("status")           val status:         String?,
            @SerializedName("description")      val description:    String?,
            @SerializedName("skill")            val skill:          String?,
            @SerializedName("email")            val email:          String?,
            @SerializedName("instagram")        val instagram:      String?,
            @SerializedName("github")           val github:         String?,
            @SerializedName("gitlab")           val gitlab:         String?,
            @SerializedName("createAt")         val createAt:       String?,
            @SerializedName("updateAt")         val updateAt:       String?

    )
}