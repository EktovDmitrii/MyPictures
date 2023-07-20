package com.example.mypictures.domain

import javax.inject.Inject

class GetAllPhotoUseCase @Inject constructor(private val repository: PhotoRepository) {
    suspend operator fun invoke(apiKey: String, page: Int, perPage: Int) =
        repository.getAllPhotos(apiKey, page, perPage)
}