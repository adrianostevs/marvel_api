package com.example.marvelapi.core

import com.example.marvelapi.core.data.local.entity.CharactersEntity
import com.example.marvelapi.core.data.local.entity.CharactersStuffEntity
import com.example.marvelapi.core.data.local.entity.ThumbnailEntity
import com.example.marvelapi.core.data.remote.model.response.ListCharactersResponse
import com.example.marvelapi.core.data.remote.model.response.ThumbnailResponse
import com.example.marvelapi.core.domain.model.Characters
import com.example.marvelapi.core.domain.model.Thumbnail

fun Characters.toCharactersStuffEntity(): CharactersStuffEntity {
    return CharactersStuffEntity(
        characters = CharactersEntity(
            id = this.id ?: "",
            name = this.name ?: "",
            description = this.description ?: "",
            isFavorite = this.isFavorite,
        ),
        thumbnail = this.thumbnail.toThumbnailEntity(this.id ?: "")
    )
}

fun Thumbnail.toThumbnailEntity(id: String): ThumbnailEntity {
    return ThumbnailEntity(
        characterId = id.toInt(),
        path = this.path ?: "",
        extension = this.extension ?: ""
    )
}

fun CharactersStuffEntity.toCharacterDomain(): Characters {
    return Characters(
        id = this.characters.id,
        name = this.characters.name,
        description = this.characters.description,
        isFavorite = this.characters.isFavorite,
        thumbnail = this.thumbnail.toThumbnailDomain()
    )
}

fun ThumbnailEntity.toThumbnailDomain(): Thumbnail {
    return Thumbnail(
        path = this.path,
        extension = this.extension
    )
}

fun ListCharactersResponse.toCharacters(): Characters {
    return Characters(
        id = this.id,
        name = this.name,
        description = this.description,
        isFavorite = false,
        thumbnail = this.thumbnail?.toThumbnail() ?: Thumbnail(null, null)
    )
}

fun ThumbnailResponse.toThumbnail(): Thumbnail {
    return Thumbnail(
        path = this.path,
        extension = this.extension
    )
}

fun Characters.toCharacters(): ListCharactersResponse {
    return ListCharactersResponse(
        id = this.id,
        name = this.name,
        description = this.description,
        thumbnail = this.thumbnail.toThumbnail()
    )
}

fun Thumbnail.toThumbnail(): ThumbnailResponse {
    return ThumbnailResponse(
        path = this.path,
        extension = this.extension
    )
}