package com.example.marvelapi.data.remote.model.response

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListCharactersResponse(

    @Expose
    @SerializedName("id")
    val id: String? = null,

    @Expose
    @SerializedName("name")
    val name: String? = null,

    @Expose
    @SerializedName("description")
    val description: String? = "No description available yet~",

    @Expose
    @SerializedName("thumbnail")
    val thumbnail: ThumbnailResponse? = null,

) : Parcelable
