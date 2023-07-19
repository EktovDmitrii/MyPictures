package com.example.mypictures.domain.models

data class PhotoUrl(
    val raw: String,
    val full: String,
    val regular: String,
    val small: String,
    val thumb: String,
    val small_s3: String,
)
