package com.example.mypictures.domain

import com.example.mypictures.domain.models.Photo

interface PhotoRepository {

    suspend fun getAllPhotos() : List<Photo>
}