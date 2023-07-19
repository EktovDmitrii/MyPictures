package com.example.mypictures.domain.models

import com.example.mypictures.domain.PhotoRepository
import javax.inject.Inject

class GetAllPhotoUseCase @Inject constructor(private val repository: PhotoRepository) {
    suspend operator fun invoke() = repository.getAllPhotos()
}