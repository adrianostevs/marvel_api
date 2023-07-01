package com.example.marvelapi.core.domain.usecase

import com.example.marvelapi.core.data.AppResult
import com.example.marvelapi.core.domain.model.Characters
import kotlinx.coroutines.flow.Flow

interface CharactersUseCase {
    fun getAllCharacters() : Flow<AppResult<List<Characters>>>
    fun setFavoriteCharacter(characters: Characters, state: Boolean): Flow<Characters>
    fun getFavoriteCharacter(): Flow<List<Characters>>
}