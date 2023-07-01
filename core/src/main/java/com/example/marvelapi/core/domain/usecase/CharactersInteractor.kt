package com.example.marvelapi.core.domain.usecase

import com.example.marvelapi.core.data.AppResult
import com.example.marvelapi.core.domain.model.Characters
import com.example.marvelapi.core.domain.repository.ICharactersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharactersInteractor @Inject constructor(private val charactersRepository: ICharactersRepository) :
    CharactersUseCase {

    override fun getAllCharacters(): Flow<AppResult<List<Characters>>> {
        return charactersRepository.getAllCharacters()
    }

    override fun setFavoriteCharacter(characters: Characters, state: Boolean): Flow<Characters> {
        return charactersRepository.setFavoriteCharacter(characters, state)
    }

    override fun getFavoriteCharacter(): Flow<List<Characters>> {
        return charactersRepository.getFavoriteCharacter()
    }
}