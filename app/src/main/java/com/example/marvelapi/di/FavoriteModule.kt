package com.example.marvelapi.di

import com.example.marvelapi.domain.usecase.CharactersUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteModule {
    fun characterUseCase(): CharactersUseCase
}