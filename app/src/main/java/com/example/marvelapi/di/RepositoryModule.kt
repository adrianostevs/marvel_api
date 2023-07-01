package com.example.marvelapi.di

import com.example.marvelapi.data.remote.CharactersRepository
import com.example.marvelapi.domain.repository.ICharactersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(charactersRepository: CharactersRepository): ICharactersRepository
}