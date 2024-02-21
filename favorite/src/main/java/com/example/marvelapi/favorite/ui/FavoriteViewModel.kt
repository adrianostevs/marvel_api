package com.example.marvelapi.favorite.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.marvelapi.core.domain.model.Characters
import com.example.marvelapi.core.domain.usecase.CharactersUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(private val charactersUseCase: CharactersUseCase) : ViewModel() {
    val characterFavorite = MutableLiveData<List<Characters>>()

    fun getFavoriteCharacter() : Job = viewModelScope.launch {
        charactersUseCase.getFavoriteCharacter().collect{
            characterFavorite.value = it
        }
    }
}