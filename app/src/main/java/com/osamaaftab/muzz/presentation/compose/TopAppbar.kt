package com.osamaaftab.muzz.presentation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.osamaaftab.muzz.R
import com.osamaaftab.muzz.presentation.theme.MuzzTypography

@Composable
fun TopAppBar(
    onBackClick: () -> Unit,
) {
    Surface(
        shadowElevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        Box(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .height(60.dp),
                verticalAlignment = Alignment.CenterVertically,

                ) {
                IconButton(
                    modifier = Modifier.padding(start = 8.dp),
                    onClick = { onBackClick() }) {
                    Icon(
                        modifier = Modifier.padding(8.dp),
                        tint = MaterialTheme.colorScheme.primary,
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_back),
                        contentDescription = "back"
                    )
                }

                CoilAsyncImage(
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .size(40.dp, 40.dp)
                )

                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = stringResource(id = R.string.avatar_name),
                    textAlign = TextAlign.Center,
                    style = MuzzTypography.titleLarge,
                )
            }
        }
    }
}

@Preview
@Composable
fun NotificationAppBarPreview() {
    TopAppBar {}
}