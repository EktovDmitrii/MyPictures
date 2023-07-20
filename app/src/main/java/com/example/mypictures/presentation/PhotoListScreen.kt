package com.example.mypictures.presentation

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.mypictures.domain.models.Photo
import com.example.mypictures.R

@Composable
fun PhotoListScreen() {
    val viewModel: PhotoViewModel = viewModel(
        key = "photoViewModelKey"
    )
    val photosState by viewModel.photosState.collectAsState()

    val lazyListState = rememberLazyGridState()
    var selectedPhoto by remember { mutableStateOf<Photo?>(null) }

    if (selectedPhoto != null) {
        ZoomableImageScreen(
            imageUrl = selectedPhoto!!.urlList.regular,
            onBack = { selectedPhoto = null }
        )
    } else {
        Surface(modifier = Modifier.fillMaxSize()) {
            when (val state = photosState) {
                is PhotoViewState.Loading -> {
                    val composition = rememberLottieComposition(
                        spec = LottieCompositionSpec.RawRes(R.raw.animation_lkb6c8yq))
                        LottieAnimation(composition = composition.value, modifier = Modifier.wrapContentSize())
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
                            PhotoItem(state.photos[index]) {
                                selectedPhoto = state.photos[index]
                            }
                        }
                        if (lazyListState.firstVisibleItemIndex + lazyListState.layoutInfo.visibleItemsInfo.size >= lazyListState.layoutInfo.totalItemsCount) {
                            viewModel.loadNextPage()
                            Log.d(
                                "pageLoader",
                                "load next page ${lazyListState.firstVisibleItemIndex}. ${lazyListState.layoutInfo.visibleItemsInfo.size}. ${lazyListState.layoutInfo.totalItemsCount}"
                            )
                        }
                    }
                }
                is PhotoViewState.Error -> {
                    Log.d("PhotoApp", "Exception in loading")
                }
            }
        }
    }
}

@Composable
fun PhotoItem(photo: Photo, onPhotoClicked: (String) -> Unit) {
    Card(
        modifier = Modifier
            .background(
                color = Color.Black,
                shape = RoundedCornerShape(12.dp),
            )
            .clickable { onPhotoClicked(photo.urlList.regular) },
        elevation = 12.dp,
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