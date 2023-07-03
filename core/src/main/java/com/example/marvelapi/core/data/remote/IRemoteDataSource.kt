package com.example.marvelapi.core.data.remote

import com.example.marvelapi.core.data.AppResult
import com.example.marvelapi.core.data.remote.model.response.ListCharactersResponse
import kotlinx.coroutines.flow.Flow

interface IRemoteDataSource {
    suspend fun getAllCharacters(limit: Int, offset: Int): Flow<AppResult<List<ListCharactersResponse>>>
}