package com.example.marvelapi.core.data.remote.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BaseResponse(

    @Expose
    @SerializedName("code")
    val code: Int? = null,

    @Expose
    @SerializedName("status")
    val status: String? = null,

    @Expose
    @SerializedName("data")
    val data: DataResponse? = null,

    )