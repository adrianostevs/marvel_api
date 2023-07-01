package com.example.marvelapi.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marvelapi.core.domain.model.Characters
import com.example.marvelapi.core.domain.usecase.CharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val charactersUseCase: CharactersUseCase) :
    ViewModel() {

    val characters = MutableLiveData<Characters>()

    fun setFavorite() = flow {
        characters.value?.let {
            emitAll(charactersUseCase.setFavoriteCharacter(it, !it.isFavorite))
        }
    }
}