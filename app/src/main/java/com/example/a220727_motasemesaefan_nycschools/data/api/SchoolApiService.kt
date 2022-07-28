package com.example.a220727_motasemesaefan_nycschools.data.api

import com.example.a220727_motasemesaefan_nycschools.data.model.SchoolItem
import com.example.a220727_motasemesaefan_nycschools.data.model.ScoreItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SchoolApiService {
    @GET(ENDPOINT_SCHOOLS)
    suspend fun getSchools(): Response<List<SchoolItem>>

    @GET(ENDPOINT_SCORE)
    suspend fun getScore(
        @Query("dbn") dbn: String
    ): Response<List<ScoreItem>>


    companion object {
        const val BASE_URL = "https://data.cityofnewyork.us/"
        const val ENDPOINT_SCHOOLS = "resource/s3k6-pzi2.json"
        const val ENDPOINT_SCORE = "resource/f9bf-2cp4.json"
    }
}