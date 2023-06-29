package com.example.marvelapi.di

import android.content.Context
import androidx.room.Room
import com.example.marvelapi.data.local.room.CharactersDao
import com.example.marvelapi.data.local.room.CharactersDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): CharactersDatabase = Room.databaseBuilder(
        context,
        CharactersDatabase::class.java, "Characters.db"
    ).fallbackToDestructiveMigration().build()


    @Provides
    fun provideTourismDao(database: CharactersDatabase): CharactersDao = database.charactersDao()

}