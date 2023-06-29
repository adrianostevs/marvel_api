package com.example.marvelapi.data.local.room

import androidx.room.Dao
import androidx.room.Query
import com.example.marvelapi.data.local.entity.CharactersStuffEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class CharactersDao {
    @Query("SELECT * FR0M characters")
    abstract fun getAllCharacters(): Flow<List<CharactersStuffEntity>>
}