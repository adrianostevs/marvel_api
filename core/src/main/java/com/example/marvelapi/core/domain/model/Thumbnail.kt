package com.example.marvelapi.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Thumbnail(
    val path: String?,
    val extension: String?
): Parcelable
