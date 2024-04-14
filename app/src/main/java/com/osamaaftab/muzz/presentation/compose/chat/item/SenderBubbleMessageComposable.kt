package com.osamaaftab.muzz.presentation.compose.chat.item

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.osamaaftab.muzz.domain.model.ChatData
import com.osamaaftab.muzz.presentation.theme.MuzzTypography

@Composable
fun SenderBubbleMessageComposable(message: ChatData.Message) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.CenterEnd
    ) {
        Surface(
            color = MaterialTheme.colorScheme.primary,
            shape = RoundedCornerShape(
                topStart = 20.dp,
                topEnd = 20.dp,
                bottomEnd = 2.dp,
                bottomStart = 20.dp
            ),
            modifier = Modifier.padding(bottom = 12.dp, start = 80.dp)
        ) {
            Text(
                text = message.messageContent,
                style = MuzzTypography.bodyMedium,
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}