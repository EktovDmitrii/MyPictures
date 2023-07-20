package com.example.mypictures.presentation

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.mypictures.domain.models.Photo

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PhotoListScreen() {
    val viewModel: PhotoViewModel = viewModel()
    val photosState = viewModel.photosState.collectAsState()

    Surface(modifier = Modifier.fillMaxSize()) {
        when (val state = photosState.value) {
            is PhotoViewState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.wrapContentSize())
            }
            is PhotoViewState.Success -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.photos.size) { index ->
                        PhotoItem(state.photos[index])
                    }
                }
            }
            is PhotoViewState.Error -> {
               Log.d("PhotoApp", "Exception in loading")
            }
        }
    }
}

@Composable
fun PhotoItem(photo: Photo) {
    Image(
        painter = rememberImagePainter(photo.urlList.small), // Выберите нужный размер изображения
        contentDescription = null, // Укажите здесь описание изображения, если необходимо
        modifier = Modifier.aspectRatio(1f) // Задайте соотношение сторон изображения
    )
}