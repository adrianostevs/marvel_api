package com.example.marvelapi.data.local.entity

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import kotlinx.parcelize.Parcelize

@Parcelize
data class CharactersStuffEntity(

    @Embedded
    val characters: CharactersEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "characterId"
    )
    val thumbnail: List<ThumbnailEntity>

) : Parcelable