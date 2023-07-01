package com.example.marvelapi.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.marvelapi.domain.usecase.CharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(charactersUseCase: CharactersUseCase) : ViewModel(){

    val charactersList = charactersUseCase.getAllCharacters().asLiveData()
}