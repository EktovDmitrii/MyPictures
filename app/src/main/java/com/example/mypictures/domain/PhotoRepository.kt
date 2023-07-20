package com.example.mypictures.domain

import com.example.mypictures.domain.models.Photo

interface PhotoRepository {

    suspend fun getAllPhotos(apiKey: String, page: Int, perPage: Int) : List<Photo>
}