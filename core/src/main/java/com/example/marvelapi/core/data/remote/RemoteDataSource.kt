package com.example.marvelapi.core.data.remote

import com.example.marvelapi.core.data.AppResult
import com.example.marvelapi.core.data.remote.model.response.ListCharactersResponse
import com.example.marvelapi.core.data.remote.network.ApiService
import com.example.marvelapi.core.utils.HashExtension
import com.example.marvelapi.core.utils.HashExtension.toHex
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getAllCharacters(
        limit: Int = 20,
        offset: Int = 0
    ): Flow<AppResult<List<ListCharactersResponse>>> = flow<AppResult<List<ListCharactersResponse>>> {
        coroutineScope {
            try {
                val apiKey = "cc52740d9acf2158f5156a6c10c004ba"
                val privateKey = "9fb2d8300bde2300a1c65d980618fe9f85bff8d9"
                val timeStamp = System.currentTimeMillis().toString()
                val hashed = HashExtension.md5(timeStamp + privateKey + apiKey).toHex()
                val response = apiService.getListCharacters(limit = limit, offset = offset, timeStamp = timeStamp, apiKey = apiKey, hash = hashed)
                val listData = response.data?.results
                if (listData != null) {
                    if (listData.isNotEmpty()) {
                        emit(AppResult.Success(listData))
                    } else {
                        emit(AppResult.Error(message = response.status ?: ""))
                    }
                }
            } catch (e: Exception) {
                emit(AppResult.Error(message = "Something went wrong"))
            }
        }
    }.flowOn(Dispatchers.IO)
}