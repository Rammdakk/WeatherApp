package com.rammdakk.avitotestproj.data.restApi

import com.rammdakk.todo.ioc.ApplicationComponentScope
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
object RetrofitModule {
    @ApplicationComponentScope
    @Provides
    fun jsonPlaceHolderApi(): JsonPlaceHolderApi {
        return Retrofit.Builder().baseUrl("https://api.openweathermap.org/data/2.5/forecast?")
            .addConverterFactory(
                GsonConverterFactory.create()
            ).client(HttpClient.client).build().create(JsonPlaceHolderApi::class.java)
    }
}