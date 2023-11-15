package com.jjg.mvvmproject.network

import com.google.gson.annotations.SerializedName

sealed class ResponseWrapper<out T : BaseResponse> {
    data class Success<out T : BaseResponse>(val body: T) : ResponseWrapper<T>()
    data class ServerError(val e: Exception) : ResponseWrapper<Nothing>()
    data class StatusError(val ErrorMeta: ErrorMeta) : ResponseWrapper<Nothing>()
}

interface BaseResponse {
    val meta: Meta?
}

data class Meta(
    @SerializedName("is_end")
    val isEnd: Boolean ,
    @SerializedName("pageable_count")
    val pageableCount: Long ,
    @SerializedName("total_count")
    val totalCount: Long ,
)

data class NetworkResponse<T>(
    val documents: T?,
    override val meta: Meta
) : BaseResponse


data class ErrorMeta(
    val errorType:String,
    val message:String,
)
