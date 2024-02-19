package com.example.marvelapi.core.datasource

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.marvelapi.core.BuildConfig
import com.example.marvelapi.core.MainDispatcherRule
import com.example.marvelapi.core.data.AppResult
import com.example.marvelapi.core.data.remote.RemoteDataSource
import com.example.marvelapi.core.data.remote.model.response.BaseResponse
import com.example.marvelapi.core.data.remote.model.response.DataResponse
import com.example.marvelapi.core.data.remote.model.response.ListCharactersResponse
import com.example.marvelapi.core.data.remote.model.response.ThumbnailResponse
import com.example.marvelapi.core.data.remote.network.ApiService
import com.example.marvelapi.core.utils.HashExtension
import com.example.marvelapi.core.utils.HashExtension.toHex
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class RemoteDataSourceTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    lateinit var apiService: ApiService

    private lateinit var remoteDataSource: RemoteDataSource

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        remoteDataSource = RemoteDataSource(apiService)
    }

    @Test
    fun getAllCharactersSuccess() {

        runBlocking {
            val listCharactersResponse = arrayListOf<ListCharactersResponse>()
            listCharactersResponse.add(
                ListCharactersResponse(
                    id = "1011334",
                    name = "3-D Man",
                    description = "",
                    thumbnail = ThumbnailResponse(path = "http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784", extension = "jpg")
                )
            )
            val privateKey = BuildConfig.PRIVATE_KEY
            val apiKey = BuildConfig.API_KEY
            val timeStamp = System.currentTimeMillis().toString()
            val hashed = HashExtension.md5(timeStamp + privateKey + apiKey).toHex()
            `when`(
                apiService.getListCharacters(
                    timeStamp = timeStamp,
                    apiKey = apiKey,
                    hash = hashed,
                    limit = 1,
                    offset = 0
                )
            ).thenReturn(BaseResponse(data = DataResponse(results = listCharactersResponse)))

            val result = remoteDataSource.getAllCharacters(limit = 1, offset = 0, timeStamp = timeStamp)

            result.collect { result ->
                if (result is AppResult.Success) {
                    Assert.assertEquals(listCharactersResponse, result.data)
                } else {
                    Assert.fail("Should Success")
                }
            }
        }
    }

    @Test
    fun getAllCharactersFailure() {
        runBlocking {
            val timeStamp = System.currentTimeMillis().toString()
            val errorMessage = "Something went wrong"
            val listCharactersResponse = arrayListOf<ListCharactersResponse>()
            listCharactersResponse.add(
                ListCharactersResponse(
                    id = "1",
                    name = "Iron Man",
                    description = "",
                    thumbnail = ThumbnailResponse(path = null, extension = null)
                )
            )
            `when`(apiService.getListCharacters(timeStamp = "1234567",
                apiKey = "1234567",
                hash = "1234",
                limit = 10,
                offset = 0)).thenThrow(RuntimeException(errorMessage))

            val result = remoteDataSource.getAllCharacters(limit = 10, offset = 0, timeStamp = timeStamp)

            result.collect { result ->
                if (result is AppResult.Error) {
                    Assert.assertEquals(errorMessage, result.message)
                } else {
                    Assert.fail("Should Error")
                }
            }
        }
    }
}