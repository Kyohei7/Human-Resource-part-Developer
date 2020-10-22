package com.example.humanresource2.service

import com.example.humanresource2.login.LoginResponse
import com.example.humanresource2.register.RegisterResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApiService {

    @FormUrlEncoded
    @POST("user/login")
    suspend fun loginRequest(@Field("email") email: String?,
                             @Field("password") password: String?) : LoginResponse

    @FormUrlEncoded
    @POST("user/register")
    suspend fun registerRequest(@Field("user_name") username: String?,
                                @Field("email") email: String?,
                                @Field("password") password: String?,
                                @Field("role") role: String?) : RegisterResponse

}