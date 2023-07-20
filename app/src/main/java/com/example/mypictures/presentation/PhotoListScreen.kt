package com.example.mypictures.presentation

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.mypictures.domain.models.Photo

@Composable
fun PhotoListScreen() {
    val viewModel: PhotoViewModel = viewModel()
    val photosState by viewModel.photosState.collectAsState()

    val lazyListState = rememberLazyGridState()

//    LaunchedEffect(lazyListState) {
//        val totalItemCount = lazyListState.layoutInfo.totalItemsCount
//        val lastVisibleItemIndex = lazyListState.layoutInfo.visibleItemsInfo
//            .lastOrNull()?.index ?: -1
//
//        if (lastVisibleItemIndex >= totalItemCount - 1) {
//            viewModel.loadNextPage()
//            Log.d("pageLoader", "load next page")
//        }
//    }

//    val lazyListState = rememberLazyListState()
//    val isScrolledToEnd = remember { mutableStateOf(false) }
//
//    LaunchedEffect(photosState) {
//        if (photosState is PhotoViewState.Success) {
//            Log.d("pageLoader", "launchedEffect")
//            isScrolledToEnd.value = lazyListState.layoutInfo.visibleItemsInfo
//                .lastOrNull()?.index == (photosState as PhotoViewState.Success).photos.size - 1
//        }
//    }
//
//    if (isScrolledToEnd.value) {
//        Log.d("pageLoader", "load next page")
//        viewModel.loadNextPage()
//        isScrolledToEnd.value = false
//    }

    Surface(modifier = Modifier.fillMaxSize()) {
        when (val state = photosState) {
            is PhotoViewState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.wrapContentSize())
            }
            is PhotoViewState.Success -> {
                LazyVerticalGrid(
                    state = lazyListState,
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(5.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(state.photos.size) { index ->
                        PhotoItem(state.photos[index])
                    }
                    if (lazyListState.firstVisibleItemIndex + lazyListState.layoutInfo.visibleItemsInfo.size >= lazyListState.layoutInfo.totalItemsCount) {
                        viewModel.loadNextPage()
                        Log.d("pageLoader", "load next page")
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
    Card(
        modifier = Modifier.background(
            color = Color.Black.copy(alpha = 0.9f),
            shape = RoundedCornerShape(12.dp),
        ), elevation = 12.dp
    ) {
        Image(
            painter = rememberImagePainter(photo.urlList.small),
            contentDescription = null,
            modifier = Modifier
                .aspectRatio(1f)
                .fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
    }
}