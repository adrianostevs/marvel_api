package com.example.marvelapi.core.di

import com.example.marvelapi.core.data.local.ILocalDataSource
import com.example.marvelapi.core.data.local.LocalDataSource
import com.example.marvelapi.core.data.remote.IRemoteDataSource
import com.example.marvelapi.core.data.remote.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @Provides
    fun provideRemoteDataSource(remoteDataSource: RemoteDataSource): IRemoteDataSource = remoteDataSource

    @Singleton
    @Provides
    fun provideLocalDataSource(localDataSource: LocalDataSource) : ILocalDataSource = localDataSource

}