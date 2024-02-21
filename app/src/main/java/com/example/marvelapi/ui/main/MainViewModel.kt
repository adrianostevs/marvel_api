package com.example.marvelapi.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.marvelapi.core.data.AppResult
import com.example.marvelapi.core.domain.model.Characters
import com.example.marvelapi.core.domain.usecase.CharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val charactersUseCase: CharactersUseCase) :
    ViewModel() {

    val charactersList = MutableLiveData<AppResult<List<Characters>>>()

    fun getAllCharacters(timeStamp: String): Job = viewModelScope.launch {
        charactersUseCase.getAllCharacters(timeStamp).collectLatest {
            charactersList.value = it
        }
    }

}