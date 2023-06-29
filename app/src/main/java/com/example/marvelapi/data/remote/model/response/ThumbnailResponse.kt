package com.example.marvelapi.data.remote.model.response

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ThumbnailResponse(

    @Expose
    @SerializedName("path")
    val path: String? = null,

    @Expose
    @SerializedName("extension")
    val extension: String? = null

): Parcelable
