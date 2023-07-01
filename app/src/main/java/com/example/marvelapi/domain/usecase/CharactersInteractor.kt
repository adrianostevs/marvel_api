package com.example.marvelapi.domain.usecase

import com.example.marvelapi.data.AppResult
import com.example.marvelapi.domain.model.Characters
import com.example.marvelapi.domain.repository.ICharactersRepository
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