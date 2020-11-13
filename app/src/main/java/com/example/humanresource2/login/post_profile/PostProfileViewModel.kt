package com.example.humanresource2.login.post_profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.humanresource2.helper.Constant
import com.example.humanresource2.helper.PreferencesHelper
import okhttp3.Callback
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response

class PostProfileViewModel: ViewModel() {

    val isPostResponse = MutableLiveData<Boolean>()

    private lateinit var service: PostProfileApiService
    private lateinit var sharePref: PreferencesHelper

    fun setSharePref(sharePref: PreferencesHelper) {
        this.sharePref = sharePref
    }

    fun setPostService(service: PostProfileApiService) {
        this.service = service
    }

    fun postDeveloper(
            nameDev: RequestBody,
            job: RequestBody,
            location: RequestBody,
            status: RequestBody,
            description: RequestBody,
            skill: RequestBody,
            email: RequestBody,
            instagram: RequestBody,
            github: RequestBody,
            gitlab: RequestBody,
            photo: MultipartBody.Part?,
            idUser: RequestBody
    ) {
            service.postDataDeveloper(nameDev, job, location, status, description, skill,
                    email, instagram, github, gitlab, photo, idUser).enqueue(object : retrofit2.Callback<PostProfileResponse> {
                override fun onFailure(call: Call<PostProfileResponse>, t: Throwable) {

                }

                override fun onResponse(
                        call: Call<PostProfileResponse>,
                        response: Response<PostProfileResponse>
                ) {
                    sharePref.putString(Constant.PREFERENCES_IS_ID_DEV, response.body()?.data?.id)
                    isPostResponse.value = true
                }

            })
    }
}