package com.example.marvelapi.data.remote.network

import okhttp3.Interceptor
import okhttp3.Response

class HttpClientInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        return chain.proceed(requestBuilder.build())
    }

}