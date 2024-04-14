package com.osamaaftab.muzz.presentation.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.osamaaftab.muzz.R

@Composable
fun CoilAsyncImage(
    imageUrl: String = "https://i.insider.com/52c96f6269beddb8064f26d4?width=300&format=jpeg",
    modifier: Modifier,
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.ic_launcher_background),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
    )
}