package com.example.marvelapi.data.remote.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DataResponse(

    @Expose
    @SerializedName("results")
    val results: List<ListCharactersResponse>? = null

)
