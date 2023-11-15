package com.jjg.mvvmproject.network

import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import timber.log.Timber

class ApiLogger : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        Timber.d(message)
    }
}