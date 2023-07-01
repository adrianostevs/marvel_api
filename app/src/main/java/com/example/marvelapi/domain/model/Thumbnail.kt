package com.example.marvelapi.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Thumbnail(
    val path: String?,
    val extension: String?
): Parcelable
