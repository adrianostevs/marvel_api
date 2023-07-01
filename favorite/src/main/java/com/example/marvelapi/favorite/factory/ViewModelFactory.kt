package com.example.marvelapi.favorite.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.marvelapi.domain.usecase.CharactersUseCase
import com.example.marvelapi.favorite.ui.FavoriteViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val charactersUseCase: CharactersUseCase) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> FavoriteViewModel(
                charactersUseCase
            ) as T
            else -> throw Throwable("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}