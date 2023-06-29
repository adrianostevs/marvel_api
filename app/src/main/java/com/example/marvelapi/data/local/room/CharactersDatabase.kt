package com.example.marvelapi.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.marvelapi.data.local.entity.CharactersEntity
import com.example.marvelapi.data.local.entity.ThumbnailEntity

@Database(
    entities = [CharactersEntity::class, ThumbnailEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CharactersDatabase : RoomDatabase(){
    abstract fun charactersDao() : CharactersDao
}