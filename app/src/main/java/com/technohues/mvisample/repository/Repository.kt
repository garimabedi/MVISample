package com.technohues.mvisample.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.technohues.mvisample.api.RetrofitBuilder
import com.technohues.mvisample.ui.main.state.MainViewState
import com.technohues.mvisample.util.ApiEmptyResponse
import com.technohues.mvisample.util.ApiErrorResponse
import com.technohues.mvisample.util.ApiSuccessResponse
import com.technohues.mvisample.util.DataState

object Repository {

    fun getBlogPosts(): LiveData<DataState<MainViewState>> {
        return Transformations.switchMap(RetrofitBuilder.apiService.getBlogPosts()) { apiResponse ->
            object : LiveData<DataState<MainViewState>>() {
                override fun onActive() {
                    super.onActive()
                    when (apiResponse) {
                        is ApiSuccessResponse -> {
                            value = DataState.data(
                                message = null,
                                data = MainViewState(blogPosts = apiResponse.body)
                            )
                        }

                        is ApiErrorResponse -> {
                            value = DataState.error(
                                message = apiResponse.errorMessage
                            )
                        }

                        is ApiEmptyResponse -> {
                            value = DataState.error(
                                message = "204 Returned Nothing!"
                            )
                        }
                    }
                }
            }
        }
    }

    fun getUser(userId: String): LiveData<DataState<MainViewState>> {
        return Transformations.switchMap(RetrofitBuilder.apiService.getUser(userId)) { apiResponse ->
            object : LiveData<DataState<MainViewState>>() {
                override fun onActive() {
                    super.onActive()
                    when (apiResponse) {
                        is ApiSuccessResponse -> {
                            value = DataState.data(
                                message = null,
                                data = MainViewState(user = apiResponse.body)
                            )
                        }

                        is ApiErrorResponse -> {
                            value = DataState.error(
                                message = apiResponse.errorMessage
                            )
                        }

                        is ApiEmptyResponse -> {
                            value = DataState.error(
                                message = "204 Returned Nothing!"
                            )
                        }
                    }
                }
            }
        }
    }
}