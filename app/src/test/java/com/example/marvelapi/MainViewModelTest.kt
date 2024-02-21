package com.example.marvelapi

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.marvelapi.core.data.AppResult
import com.example.marvelapi.core.domain.model.Characters
import com.example.marvelapi.core.domain.model.Thumbnail
import com.example.marvelapi.core.domain.usecase.CharactersUseCase
import com.example.marvelapi.ui.main.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    lateinit var charactersUseCase: CharactersUseCase

    lateinit var mainViewModel: MainViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        mainViewModel = MainViewModel(charactersUseCase)
    }

    @Test
    fun getListCharacterTest() {
        runTest {
            val timestamp = System.currentTimeMillis().toString()
            val characterList = arrayListOf<Characters>()
            characterList.add(
                Characters(
                    id = "1011334",
                    name = "3-D Man",
                    description = "",
                    isFavorite = false,
                    thumbnail = Thumbnail(
                        path = "http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784",
                        extension = "jpg"
                    ),
                )
            )

            `when`(charactersUseCase.getAllCharacters(timestamp))
                .thenReturn(flowOf(AppResult.Success(characterList)))

            mainViewModel.getAllCharacters(timestamp)

            Assert.assertEquals(
                characterList,
                (mainViewModel.charactersList.value as AppResult.Success).data
            )

        }
    }
}