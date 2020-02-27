package com.technohues.mvisample.api

import androidx.lifecycle.LiveData
import com.technohues.mvisample.model.BlogPost
import com.technohues.mvisample.model.User
import com.technohues.mvisample.util.GenericApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {  // used to make those requests

    @GET("placeholder/blogs")
    // usually Retrofit requests return Call objects but we will convert the Retrofit Call object to LiveData objects
    fun getBlogPosts(): LiveData<GenericApiResponse<List<BlogPost>>>

    @GET("placeholder/user/{userId}")
    fun getUser(
        @Path("userId") userId: String      // this value will be filled by {userId}
    ): LiveData<GenericApiResponse<User>>
}