package com.example.marvelapi.core.data.remote

import com.example.marvelapi.core.data.AppResult
import com.example.marvelapi.core.data.local.ILocalDataSource
import com.example.marvelapi.core.data.local.entity.CharactersEntity
import com.example.marvelapi.core.data.local.entity.CharactersStuffEntity
import com.example.marvelapi.core.data.local.entity.ThumbnailEntity
import com.example.marvelapi.core.data.remote.model.response.ListCharactersResponse
import com.example.marvelapi.core.domain.model.Characters
import com.example.marvelapi.core.domain.model.Thumbnail
import com.example.marvelapi.core.domain.repository.ICharactersRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharactersRepository @Inject constructor(
    private val remoteDataSource: IRemoteDataSource,
    private val localDataSource: ILocalDataSource
) : ICharactersRepository {

    override fun getAllCharacters(): Flow<AppResult<List<Characters>>> =
        object : NetworkBoundResource<List<Characters>, List<ListCharactersResponse>>() {
            override fun loadFromDb(): Flow<List<Characters>> {
                return localDataSource.getAllCharacters().map {
                    it.map { data ->
                        Characters(
                            id = data.characters.id,
                            name = data.characters.name,
                            description = data.characters.description,
                            isFavorite = data.characters.isFavorite,
                            thumbnail = Thumbnail(
                                path = data.thumbnail.path,
                                extension = data.thumbnail.extension
                            )
                        )
                    }
                }
            }

            override suspend fun createCall(): Flow<AppResult<List<ListCharactersResponse>>> {
                return remoteDataSource.getAllCharacters(limit = 20, offset = 0)
            }

            override suspend fun saveCallResult(data: List<ListCharactersResponse>) {
                val characterList = mutableListOf<CharactersStuffEntity>()
                val entities = data.map {
                    CharactersStuffEntity(
                        characters = CharactersEntity(
                            id = it.id ?: "",
                            name = it.name ?: "",
                            isFavorite = false,
                            description = it.description ?: "",
                        ),
                        thumbnail = ThumbnailEntity(
                            path = it.thumbnail?.path ?: "",
                            extension = it.thumbnail?.extension ?: "",
                            characterId = it.id?.toInt() ?: 0
                        ),
                    )
                }
                characterList.addAll(entities)
                return localDataSource.insertCharactersStuff(characterList)
            }

            override fun shouldFetch(data: List<Characters>?): Boolean {
                return data.isNullOrEmpty()
            }

        }.asFlow()

    override fun setFavoriteCharacter(characters: Characters, state: Boolean): Flow<Characters> =
        flow {
            val result =
                withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                    localDataSource.setFavoriteCharacters(
                        characters = characters.toCharactersStuffEntity().characters,
                        state
                    )
                }
            emitAll(result.map { it.toCharacterDomain() })
        }

    override fun getFavoriteCharacter(): Flow<List<Characters>> {
        return localDataSource.getFavoriteCharacters().map {
            it.map { data ->
                Characters(
                    id = data.characters.id,
                    name = data.characters.name,
                    description = data.characters.description,
                    isFavorite = data.characters.isFavorite,
                    thumbnail = Thumbnail(
                        path = data.thumbnail.path,
                        extension = data.thumbnail.extension
                    )
                )
            }
        }
    }

    private fun Characters.toCharactersStuffEntity(): CharactersStuffEntity {
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

    private fun Thumbnail.toThumbnailEntity(id: String): ThumbnailEntity {
        return ThumbnailEntity(
            characterId = id.toInt(),
            path = this.path ?: "",
            extension = this.extension ?: ""
        )
    }

    private fun CharactersStuffEntity.toCharacterDomain(): Characters {
        return Characters(
            id = this.characters.id,
            name = this.characters.name,
            description = this.characters.description,
            isFavorite = this.characters.isFavorite,
            thumbnail = this.thumbnail.toThumbnailDomain()
        )
    }

    private fun ThumbnailEntity.toThumbnailDomain(): Thumbnail {
        return Thumbnail(
            path = this.path,
            extension = this.extension
        )
    }
}