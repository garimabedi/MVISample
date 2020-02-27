package com.technohues.mvisample.api

import com.technohues.mvisample.util.LiveDataCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Singleton in Kotlin
object RetrofitBuilder {

    const val BASE_URL = "https://open-api.xyz/"
    // by lazy means only initiliaze once and use the same instance
    val retrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // convert json objects to java objects
            .addCallAdapterFactory(LiveDataCallAdapterFactory())    // convert Retrofit Call object to LiveData object
    }

    val apiService: ApiService by lazy {
        retrofitBuilder
            .build()
            .create(ApiService::class.java)
    }
}