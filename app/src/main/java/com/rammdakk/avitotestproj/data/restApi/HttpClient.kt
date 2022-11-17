package com.rammdakk.avitotestproj.data.restApi

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor

object HttpClient {

    private fun getLogs(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }


    val client: OkHttpClient =
        OkHttpClient.Builder().addInterceptor(getLogs()).build()

}

//class AuthInterceptor : Interceptor {
//    override fun intercept(chain: Interceptor.Chain): Response {
//        val requestBuilder = chain.request().newBuilder()
//        requestBuilder.addQueryParameter("appid", "7cd5c37626cce6c6d36726c58b931504")
//        return chain.proceed(requestBuilder.build())
//    }
//}

