package com.example.mypictures.data

import com.example.mypictures.domain.PhotoRepository
import com.example.mypictures.domain.models.Photo
import com.example.mypictures.domain.models.PhotoUrl
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : PhotoRepository {

    override suspend fun getAllPhotos(apiKey: String, page: Int, perPage: Int): List<Photo> {
        return apiService.getPhotoList(apiKey, page, perPage).map {
            Photo(
                id = it.id,
                urlList = PhotoUrl(
                    raw = it.urlList.raw,
                    full = it.urlList.full,
                    regular = it.urlList.regular,
                    small = it.urlList.small,
                    thumb = it.urlList.thumb,
                    small_s3 = it.urlList.small_s3
                )
            )
        }
    }
}