package com.osamaaftab.muzz.presentation.compose.chat.item

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.osamaaftab.muzz.domain.model.ChatData
import com.osamaaftab.muzz.presentation.theme.MuzzTypography
import com.osamaaftab.muzz.presentation.theme.replyBubbleBackground

@Composable
fun ReceiverBubbleMessageComposable(message: ChatData.Message) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.CenterStart
    ) {
        Surface(
            color = replyBubbleBackground,
            shape = RoundedCornerShape(
                topStart = 20.dp,
                topEnd = 20.dp,
                bottomEnd = 20.dp,
                bottomStart = 2.dp
            ),
            modifier = Modifier.padding(bottom = 12.dp, end = 80.dp)
        ) {
            Text(
                text = message.messageContent,
                style = MuzzTypography.bodyMedium,
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}
