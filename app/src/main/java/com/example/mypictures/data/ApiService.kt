package com.example.mypictures.data

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("photos/")
    suspend fun getPhotoList(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = API_KEY_VALUE,
    ): List<PhotoDto>

    companion object {
        private const val QUERY_PARAM_API_KEY = "client_id"
        private const val API_KEY_VALUE = "XMwtmLsFSbNB-oGlyH7BlMdsTL-gVGXHQaMOSPUH7ew"
    }
}