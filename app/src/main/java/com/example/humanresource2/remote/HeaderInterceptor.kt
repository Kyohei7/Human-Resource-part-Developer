package com.example.humanresource2.remote

import android.content.Context
import com.example.humanresource2.helper.Constant
import com.example.humanresource2.helper.PreferencesHelper
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(var mContext: Context) : Interceptor {

    private lateinit var sharePreferencesHelper: PreferencesHelper

    override fun intercept(chain: Interceptor.Chain): Response = chain.run {

        sharePreferencesHelper = PreferencesHelper(mContext)

        val token = sharePreferencesHelper.getString(Constant.PREFERENCES_IS_TOKEN)
        proceed(
            request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        )
    }
}