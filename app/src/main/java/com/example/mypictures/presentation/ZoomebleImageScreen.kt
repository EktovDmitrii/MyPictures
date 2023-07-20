package com.example.mypictures.presentation

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import kotlinx.coroutines.launch

@Composable
fun ZoomableImageScreen(
    imageUrl: String,
    onBack: () -> Unit
) {
    val scale = remember { mutableStateOf(1f) }
    val offsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }

    val coroutineScope = rememberCoroutineScope()


    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = rememberImagePainter(imageUrl),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTransformGestures { _, pan, gestureZoom, _ ->
                        scale.value *= gestureZoom
                        if (scale.value > 1f) {
                            coroutineScope.launch {
                                offsetX.snapTo(offsetX.value + pan.x)
                                offsetY.snapTo(offsetY.value + pan.y)
                            }
                        } else {
                            coroutineScope.launch {
                                offsetX.animateTo(0f)
                                offsetY.animateTo(0f)
                            }
                        }
                    }
                }
                .graphicsLayer(
                    scaleX = scale.value,
                    scaleY = scale.value,
                    translationX = offsetX.value,
                    translationY = offsetY.value
                ),
            contentScale = ContentScale.Fit,
        )
        IconButton(
            onClick = onBack,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopStart)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black
            )
        }
    }
}