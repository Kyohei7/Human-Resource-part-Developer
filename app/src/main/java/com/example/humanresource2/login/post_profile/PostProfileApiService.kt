package com.example.humanresource2.login.post_profile

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface PostProfileApiService {

    @Multipart
    @POST("developer")
    fun postDataDeveloper(
            @Part("name_developer") nameDeveloper: RequestBody,
            @Part photo: MultipartBody.Part?,
            @Part("job") job: RequestBody,
            @Part("location") location : RequestBody,
            @Part("status") status : RequestBody,
            @Part("description") description : RequestBody,
            @Part("skill") skill : RequestBody,
            @Part("email") email : RequestBody,
            @Part("instagram") instagram : RequestBody,
            @Part("github") github : RequestBody,
            @Part("gitlab") gitlab : RequestBody,
            @Part("id_user") idUser : RequestBody) : Call<PostProfileResponse>


}