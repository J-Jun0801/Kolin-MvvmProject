package com.jjg.mvvmproject.repository

import com.google.gson.Gson
import com.jjg.mvvmproject.network.BaseResponse
import com.jjg.mvvmproject.network.ErrorMeta
import com.jjg.mvvmproject.network.ResponseWrapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.CancellationException

open class NetworkRepository {

    suspend fun <T : BaseResponse> apiCall(api: suspend () -> Response<T>): ResponseWrapper<T> {

        val response = api()

        return if (response.isSuccessful) {
            ResponseWrapper.Success(response.body()!!)
        } else {
            try {
                val errorMeta = Gson().fromJson(response.errorBody()!!.string(), ErrorMeta::class.java)
                ResponseWrapper.StatusError(errorMeta)
            } catch (exception: Exception) {
                ResponseWrapper.ServerError(exception)
            }

        }
    }
}
