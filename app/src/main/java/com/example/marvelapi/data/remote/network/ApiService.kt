package com.example.marvelapi.data.remote.network

import com.example.marvelapi.data.remote.model.response.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ApiService {

    @GET("characters")
    suspend fun getListCharacters(
        @Query("apikey") apiKey: String = "cc52740d9acf2158f5156a6c10c004ba",
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ) : BaseResponse

}