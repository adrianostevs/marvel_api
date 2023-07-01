package com.example.marvelapi.core.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.marvelapi.core.data.local.entity.CharactersEntity
import com.example.marvelapi.core.data.local.entity.ThumbnailEntity

@Database(
    entities = [CharactersEntity::class, ThumbnailEntity::class],
    version = 2,
    exportSchema = false
)
abstract class CharactersDatabase : RoomDatabase(){
    abstract fun charactersDao() : CharactersDao
}