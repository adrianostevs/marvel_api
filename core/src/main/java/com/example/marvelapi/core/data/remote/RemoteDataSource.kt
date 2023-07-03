package com.example.marvelapi.core.data.remote

import com.example.marvelapi.core.BuildConfig
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
class RemoteDataSource @Inject constructor(private val apiService: ApiService) : IRemoteDataSource {

    override suspend fun getAllCharacters(
        limit: Int,
        offset: Int
    ): Flow<AppResult<List<ListCharactersResponse>>> = flow<AppResult<List<ListCharactersResponse>>> {
        coroutineScope {
            try {
                val apiKey = BuildConfig.API_KEY
                val privateKey = BuildConfig.PRIVATE_KEY
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