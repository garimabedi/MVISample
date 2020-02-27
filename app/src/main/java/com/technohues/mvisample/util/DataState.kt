package com.technohues.mvisample.util

data class DataState<T>(
    var message: String? = null,
    var isLoading: Boolean = false,
    var data: T? = null
) {
    companion object {
        fun <T> error(message: String?): DataState<T> {
            return DataState(
                message = message,
                isLoading = false,
                data = null
            )
        }

        fun <T> loading(isLoading: Boolean): DataState<T> {
            return DataState(
                message = null,
                isLoading = isLoading,
                data = null
            )
        }

        fun <T> data(message: String?, data: T? = null): DataState<T> {
            return DataState(
                message = message,
                isLoading = false,
                data = data
            )
        }
    }

    override fun toString(): String {
        return "DataState(\nmessage=$message,\n loading=$isLoading,\n data=$data)"
    }
}