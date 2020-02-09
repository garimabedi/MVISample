package com.technohues.mvisample.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Singleton in Kotlin
object MyRetrofitBuilder {

    const val BASE_URL = "https://open-api.xyz/placeholder"
    val retrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    val apiService: ApiService by lazy {
        retrofitBuilder
            .build()
            .create(ApiService::class.java)
    }
}