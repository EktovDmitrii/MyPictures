package com.example.mypictures.data

import retrofit2.http.GET

interface ApiService {

    @GET("photos/{}")
    suspend fun getPhotoList() : List<PhotoDto>
}