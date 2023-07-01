package com.example.marvelapi.domain.repository

import com.example.marvelapi.data.AppResult
import com.example.marvelapi.domain.model.Characters
import kotlinx.coroutines.flow.Flow

interface ICharactersRepository {
    fun getAllCharacters(): Flow<AppResult<List<Characters>>>
    fun setFavoriteCharacter(characters: Characters, state: Boolean): Flow<Characters>
    fun getFavoriteCharacter(): Flow<List<Characters>>
}