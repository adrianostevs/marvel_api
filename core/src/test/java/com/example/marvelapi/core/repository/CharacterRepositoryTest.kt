package com.example.marvelapi.core.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.marvelapi.core.MainDispatcherRule
import com.example.marvelapi.core.data.AppResult
import com.example.marvelapi.core.data.local.LocalDataSource
import com.example.marvelapi.core.data.local.entity.CharactersStuffEntity
import com.example.marvelapi.core.data.remote.CharactersRepository
import com.example.marvelapi.core.data.remote.RemoteDataSource
import com.example.marvelapi.core.data.remote.model.response.ListCharactersResponse
import com.example.marvelapi.core.data.remote.model.response.ThumbnailResponse
import com.example.marvelapi.core.data.remote.network.ApiService
import com.example.marvelapi.core.domain.model.Characters
import com.example.marvelapi.core.domain.model.Thumbnail
import com.example.marvelapi.core.toCharacters
import com.example.marvelapi.core.toCharactersStuffEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class CharacterRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    lateinit var remoteDataSource: RemoteDataSource

    @Mock
    lateinit var localDataSource: LocalDataSource

    private lateinit var charactersRepository: CharactersRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        charactersRepository = CharactersRepository(remoteDataSource, localDataSource)
    }

    @Test
    fun getAllCharacterFromLocal() {
        runTest {
            val timeStamp = System.currentTimeMillis().toString()
            val listCharactersStuffEntity = mutableListOf<CharactersStuffEntity>()
            val listCharacter = mutableListOf<Characters>()
            val characters = Characters(
                id = "1011334",
                name = "3-D Man",
                description = "",
                isFavorite = false,
                thumbnail = Thumbnail(
                    path = "http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784",
                    extension = "jpg"
                ),
            )
            listCharacter.add(characters)
            listCharactersStuffEntity.add(characters.toCharactersStuffEntity())

            val expected = flow { emit(listCharactersStuffEntity) }

            `when`(localDataSource.getAllCharacters()).thenReturn(expected)

            val result = charactersRepository.getAllCharacters(timeStamp).first()

            Assert.assertNotNull(result)
            Assert.assertEquals(listCharacter, (result as AppResult.Success).data)
        }
    }

    @Test
    fun setFavoriteCharactersSuccess() {
        runTest {
            val characters = Characters(
                id = "1",
                name = "Iron Man",
                description = "Genius, billionaire, playboy, philanthropist",
                isFavorite = false,
                thumbnail = Thumbnail("http://example.com/ironman", "jpg")
            )

            `when`(
                localDataSource.setFavoriteCharacters(
                    characters.toCharactersStuffEntity().characters,
                    true
                )
            ).thenReturn(
                flow { emit(characters.toCharactersStuffEntity()) })

            val result = charactersRepository.setFavoriteCharacter(characters, true).first()

            Assert.assertNotNull(result)
            Assert.assertEquals(characters, result)
        }
    }

    @Test
    fun getFavoriteCharactersSuccess() {
        runTest {
            val characters = Characters(
                id = "1",
                name = "Iron Man",
                description = "Genius, billionaire, playboy, philanthropist",
                isFavorite = false,
                thumbnail = Thumbnail("http://example.com/ironman", "jpg")
            )

            `when`(localDataSource.getFavoriteCharacters()).thenReturn(flow { emit(listOf(characters.toCharactersStuffEntity())) })

            val result = charactersRepository.getFavoriteCharacter().first()

            Assert.assertNotNull(result)
            Assert.assertEquals(listOf(characters), result)
        }
    }
}