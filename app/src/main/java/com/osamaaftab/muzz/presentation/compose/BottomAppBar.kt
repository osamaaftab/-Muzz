package com.osamaaftab.muzz.presentation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.osamaaftab.muzz.R
import com.osamaaftab.muzz.presentation.state.ChatState
import com.osamaaftab.muzz.presentation.theme.unSelectedBackground


@Composable
fun BottomAppBar(
    chatState: ChatState,
    onSendMessage: () -> Unit,
    onValueChange: (String) -> Unit
) {
    Surface(
        shadowElevation = 12.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(85.dp)
    ) {
        Box(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
        ) {

            Row(
                modifier = Modifier
                    .padding(start = 12.dp, end = 12.dp)
                    .align(Alignment.Center)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,

                ) {

                CustomTextFieldApp(
                    placeholder = stringResource(id = R.string.type_message),
                    text = chatState.message,
                    onValueChange = { message ->
                        onValueChange(message)
                    },
                    keyboardType = KeyboardType.Text,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 12.dp),
                )

                // Send button
                IconButton(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.primary,
                                    MaterialTheme.colorScheme.secondary
                                )
                            )
                        ),
                    onClick = {
                        onSendMessage()
                    },
                    enabled = chatState.message.isNotBlank()
                ) {
                    if (chatState.message.isNotBlank()) {
                        addSendIcon(MaterialTheme.colorScheme.background)
                    } else {
                        addSendIcon(unSelectedBackground)
                    }
                }
            }
        }
    }
}

@Composable
private fun addSendIcon(tintColor: Color) {
    Icon(
        modifier = Modifier.padding(6.dp),
        tint = tintColor,
        imageVector = ImageVector.vectorResource(id = R.drawable.ic_send),
        contentDescription = "back"
    )
}


@Preview
@Composable
fun ChatBottomBarPreview() {
    BottomAppBar(ChatState(),{},{})
}