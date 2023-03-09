package com.nong.amazingnews.network

import retrofit2.Response

data class NewsResponse<T>(
    val status: Status,
    val data: Response<T>?,
    val exception: Exception?
) {

    sealed class Status {
        object Success : Status()
        object Failure : Status()
    }

    val isFailed: Boolean get() = this.status == Status.Failure
    val isSuccess: Boolean get() = !isFailed && data?.isSuccessful == true
    val body: T get() = data!!.body()!!

    companion object {
        fun <T> success(data: Response<T>): NewsResponse<T> {
            return NewsResponse(
                Status.Success,
                data,
                null
            )
        }

        fun <T> failure(exception: Exception): NewsResponse<T> {
            return NewsResponse(
                Status.Failure,
                null,
                exception
            )
        }
    }
}