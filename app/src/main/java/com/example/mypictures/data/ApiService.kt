package com.example.mypictures.data

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("photos/")
    suspend fun getPhotoList(
        @Query(QUERY_PARAM_API_KEY) apiKey: String,
        @Query(PAGE_ID) page: Int,
        @Query(PHOTO_PER_PAGE) perPage: Int,
    ): List<PhotoDto>

    companion object {
        private const val QUERY_PARAM_API_KEY = "client_id"
        private const val PAGE_ID = "page"
        private const val PHOTO_PER_PAGE = "per_page"
    }
}