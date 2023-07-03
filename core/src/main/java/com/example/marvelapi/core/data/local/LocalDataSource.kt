package com.example.marvelapi.core.data.local

import com.example.marvelapi.core.data.local.entity.CharactersEntity
import com.example.marvelapi.core.data.local.entity.CharactersStuffEntity
import com.example.marvelapi.core.data.local.room.CharactersDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val charactersDao: CharactersDao) :
    ILocalDataSource {

    override fun getAllCharacters(): Flow<List<CharactersStuffEntity>> =
        charactersDao.getAllCharacters()

    override fun getCharactersById(characterId: Int): Flow<CharactersStuffEntity> =
        charactersDao.getCharactersById(characterId)

    override fun getFavoriteCharacters(): Flow<List<CharactersStuffEntity>> =
        charactersDao.getFavoriteCharacters()

    override suspend fun insertCharactersStuff(characters: List<CharactersStuffEntity>) =
        charactersDao.insertCharactersStuff(characters)

    override suspend fun setFavoriteCharacters(
        characters: CharactersEntity,
        newState: Boolean
    ): Flow<CharactersStuffEntity> =
        flow {
            characters.isFavorite = newState
            charactersDao.updateCharacterFavorite(characters.id, newState)

            val newCharacters = getCharactersById(characters.id.toInt()).first()
            emit(
                newCharacters
            )
        }
}