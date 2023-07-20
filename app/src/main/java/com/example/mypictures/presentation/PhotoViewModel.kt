package com.example.mypictures.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypictures.domain.GetAllPhotoUseCase
import com.example.mypictures.domain.models.Photo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(
    private val getAllPhotoUseCase: GetAllPhotoUseCase,
) : ViewModel() {

    private val _photosState = MutableStateFlow<PhotoViewState>(PhotoViewState.Loading)
    val photosState: StateFlow<PhotoViewState> = _photosState

    init {
        fetchPhotos()
    }


    private fun fetchPhotos() {
        viewModelScope.launch {
            try {
                val photos = getAllPhotoUseCase()
                _photosState.value = PhotoViewState.Success(photos)
            } catch (e: Exception) {
                _photosState.value = PhotoViewState.Error(e.message ?: "Unknown error")
            }
        }
    }
}


sealed class PhotoViewState {
    object Loading : PhotoViewState()
    data class Success(val photos: List<Photo>) : PhotoViewState()
    data class Error(val error: String) : PhotoViewState()
}