package com.example.marvelapi.core.data.local.room

import androidx.room.*
import com.example.marvelapi.core.data.local.entity.CharactersEntity
import com.example.marvelapi.core.data.local.entity.CharactersStuffEntity
import com.example.marvelapi.core.data.local.entity.ThumbnailEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class CharactersDao {
    @Query("SELECT * FROM characters")
    abstract fun getAllCharacters(): Flow<List<CharactersStuffEntity>>

    @Query("SELECT * FROM characters WHERE id=:characterId")
    abstract fun getCharactersById(characterId: Int): Flow<CharactersStuffEntity>


    @Query("SELECT * FROM characters WHERE isFavorite=1")
    abstract fun getFavoriteCharacters(): Flow<List<CharactersStuffEntity>>

    @Transaction
    open suspend fun insertCharactersStuff(characters: List<CharactersStuffEntity>) {
        for(it in characters){
            insertCharacter(it.characters)
            deleteThumbnail(it.thumbnail.characterId)
            insertThumbnail(it.thumbnail)
        }
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertCharacter(character: CharactersEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertThumbnail(thumbnail: ThumbnailEntity)

    @Query("DELETE FROM thumbnail WHERE characterId=:characterId")
    abstract suspend fun deleteThumbnail(characterId: Int)

    @Query("UPDATE characters SET isFavorite= :isFavorite WHERE id=:id")
    abstract suspend fun updateCharacterFavorite(id: String, isFavorite: Boolean)
}