package com.example.marvelapi.di

import com.example.marvelapi.core.domain.usecase.CharactersInteractor
import com.example.marvelapi.core.domain.usecase.CharactersUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class  AppModule {

    @Binds
    @Singleton
    abstract fun provideCharactersUseCase(charactersInteractor: CharactersInteractor): CharactersUseCase

}