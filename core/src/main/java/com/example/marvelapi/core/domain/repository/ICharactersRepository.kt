package com.example.marvelapi.core.domain.repository

import com.example.marvelapi.core.data.AppResult
import com.example.marvelapi.core.domain.model.Characters
import kotlinx.coroutines.flow.Flow

interface ICharactersRepository {
    fun getAllCharacters(timeStamp: String): Flow<AppResult<List<Characters>>>
    fun setFavoriteCharacter(characters: Characters, state: Boolean): Flow<Characters>
    fun getFavoriteCharacter(): Flow<List<Characters>>
}