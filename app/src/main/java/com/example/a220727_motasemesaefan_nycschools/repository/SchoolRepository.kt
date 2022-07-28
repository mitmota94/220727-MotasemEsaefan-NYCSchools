package com.example.a220727_motasemesaefan_nycschools.repository

import com.example.a220727_motasemesaefan_nycschools.data.api.SchoolApiService
import com.example.a220727_motasemesaefan_nycschools.data.util.UIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface SchoolRepository {
    suspend fun getSchools(): Flow<UIState>
    suspend fun getScore(dbn: String): Flow<UIState>
}

class SchoolRepositoryImpl @Inject constructor(
    private val service: SchoolApiService
): SchoolRepository {
    override suspend fun getSchools(): Flow<UIState> =
        flow {
            emit(UIState.Loading)
            try {
                val response = service.getSchools()
                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(UIState.Success(it))
                    } ?: throw Exception("Response is null")
                } else throw Exception("Failed School network call")
            } catch (e: Exception) {
                emit(UIState.Error(e))
            }
        }

    override suspend fun getScore(dbn: String): Flow<UIState> =
        flow {
            try {
                val response = service.getScore(dbn)
                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(UIState.Success(it))
                    } ?: throw Exception("Response is null")
                } else throw Exception("Failed Score network call")
            } catch (e: Exception) {
                emit(UIState.Error(e))
            }
        }
}