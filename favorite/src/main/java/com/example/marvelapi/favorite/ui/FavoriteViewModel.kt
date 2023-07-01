package com.example.marvelapi.favorite.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.marvelapi.domain.usecase.CharactersUseCase
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(charactersUseCase: CharactersUseCase) : ViewModel() {
    val characterFavorite = charactersUseCase.getFavoriteCharacter().asLiveData()
}