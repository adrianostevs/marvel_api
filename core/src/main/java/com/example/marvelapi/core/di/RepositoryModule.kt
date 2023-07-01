package com.example.marvelapi.core.di

import com.example.marvelapi.core.data.remote.CharactersRepository
import com.example.marvelapi.core.domain.repository.ICharactersRepository
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