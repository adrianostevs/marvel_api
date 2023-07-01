package com.example.marvelapi.data.remote

import com.example.marvelapi.data.AppResult
import kotlinx.coroutines.flow.*

abstract class NetworkBoundResource<ResultType, RequestType> {
    private val result: Flow<AppResult<ResultType>> = flow {
        emit(AppResult.Loading())
        val dbSource = loadFromDb().first()
        if (shouldFetch(dbSource)) {
            emit(AppResult.Loading())
            when (val apiResponse = createCall().first()) {
                is AppResult.Success<*> -> {
                    apiResponse.data?.let { saveCallResult(it) }
                    emitAll(loadFromDb().map { AppResult.Success(it) })
                }
                is AppResult.Error<*> -> {
                    onFetchFailed()
                    emitAll(loadFromDb().map { AppResult.Success(it) })
                    emit(AppResult.Error(apiResponse.message ?: ""))
                }
                else -> {
                    emitAll(loadFromDb().map { AppResult.Success(it) })
                }
            }
        } else {
            emitAll(loadFromDb().map { AppResult.Success(it) })
        }
    }

    protected open fun onFetchFailed() {}
    protected abstract fun loadFromDb(): Flow<ResultType>
    protected abstract fun shouldFetch(data: ResultType?): Boolean
    protected abstract suspend fun createCall(): Flow<AppResult<RequestType>>
    protected abstract suspend fun saveCallResult(data: RequestType)
    fun asFlow(): Flow<AppResult<ResultType>> = result
}