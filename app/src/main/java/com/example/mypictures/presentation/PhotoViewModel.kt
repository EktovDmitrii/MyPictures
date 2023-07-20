package com.example.mypictures.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypictures.domain.GetAllPhotoUseCase
import com.example.mypictures.domain.models.Photo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(
    private val getAllPhotoUseCase: GetAllPhotoUseCase,
) : ViewModel() {

    private var currentPage = 1
    private val perPage = 20
//    private val apiKey = "XMwtmLsFSbNB-oGlyH7BlMdsTL-gVGXHQaMOSPUH7ew"
    private val apiKey = "SnsTfVwPVzPkbM6dmIqQR23ZP3dyHNrm4wXOWCydeow"


    private val _photosState = MutableStateFlow<PhotoViewState>(PhotoViewState.Loading)
    val photosState: StateFlow<PhotoViewState> = _photosState

    private var isLoading = false

    init {
        fetchPhotos()
    }


    private fun fetchPhotos() {
        if (isLoading) return
        viewModelScope.launch {
            try {
                val photos =
                    getAllPhotoUseCase(apiKey = apiKey, page = currentPage, perPage = perPage)
                val currentPhotos =
                    (_photosState.value as? PhotoViewState.Success)?.photos.orEmpty()
                val updatedPhotos = currentPhotos + photos
                _photosState.value = PhotoViewState.Success(updatedPhotos)
            } catch (e: Exception) {
                _photosState.value = PhotoViewState.Error(e.message ?: "Unknown error in error state")
                isLoading = false
                e.printStackTrace()            }
        }
    }

    fun loadNextPage() {
        currentPage++
        viewModelScope.launch(Dispatchers.IO) {
            delay(500)
            fetchPhotos()
            Log.d("pageLoader", "loaded page $currentPage")
        }

    }
}


sealed class PhotoViewState {
    object Loading : PhotoViewState()
    data class Success(val photos: List<Photo>) : PhotoViewState()
    data class Error(val error: String) : PhotoViewState()
}