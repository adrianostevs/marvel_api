package com.example.marvelapi.core.data.local

import com.example.marvelapi.core.data.local.entity.CharactersEntity
import com.example.marvelapi.core.data.local.entity.CharactersStuffEntity
import kotlinx.coroutines.flow.Flow

interface ILocalDataSource {

    fun getAllCharacters(): Flow<List<CharactersStuffEntity>>

    fun getCharactersById(characterId: Int): Flow<CharactersStuffEntity>

    fun getFavoriteCharacters(): Flow<List<CharactersStuffEntity>>

    suspend fun insertCharactersStuff(characters: List<CharactersStuffEntity>)

    suspend fun setFavoriteCharacters(
        characters: CharactersEntity,
        newState: Boolean
    ): Flow<CharactersStuffEntity>
}