package com.example.marvelapi.core.di

import android.content.Context
import androidx.room.Room
import com.example.marvelapi.core.data.local.room.CharactersDao
import com.example.marvelapi.core.data.local.room.CharactersDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): CharactersDatabase {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("characters".toCharArray())
        val factory = SupportFactory(passphrase)
        return Room.databaseBuilder(
            context,
            CharactersDatabase::class.java, "Characters.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }


    @Provides
    fun provideTourismDao(database: CharactersDatabase): CharactersDao = database.charactersDao()

}