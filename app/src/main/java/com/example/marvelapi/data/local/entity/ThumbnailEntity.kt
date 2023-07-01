package com.example.marvelapi.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "thumbnail", primaryKeys = ["characterId", "path"])
data class ThumbnailEntity(

    @ColumnInfo(name = "characterId")
    var characterId: Int,

    @ColumnInfo(name = "path")
    var path: String,

    @ColumnInfo(name = "extension")
    val extension: String,

): Parcelable
