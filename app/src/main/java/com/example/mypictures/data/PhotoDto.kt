package com.example.mypictures.data

import com.google.gson.annotations.SerializedName

data class PhotoDto(
    @SerializedName("id") val id: String,
    @SerializedName("urls") val urlList: PhotoUrlDto,
)