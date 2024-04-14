package com.osamaaftab.muzz.presentation.compose.chat

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.osamaaftab.muzz.domain.model.ChatData
import com.osamaaftab.muzz.domain.model.ChatItemModel
import com.osamaaftab.muzz.domain.model.ChatItemType
import com.osamaaftab.muzz.domain.model.MessageType
import com.osamaaftab.muzz.presentation.compose.BottomAppBar
import com.osamaaftab.muzz.presentation.compose.TopAppBar
import com.osamaaftab.muzz.presentation.compose.chat.item.MessageDateComposable
import com.osamaaftab.muzz.presentation.compose.chat.item.ReceiverBubbleMessageComposable
import com.osamaaftab.muzz.presentation.compose.chat.item.SenderBubbleMessageComposable
import com.osamaaftab.muzz.presentation.event.ChatEvent
import com.osamaaftab.muzz.presentation.state.ChatState
import com.osamaaftab.muzz.presentation.viewmodel.ChatViewModel


@Composable
fun ChatScreen(chatViewModel: ChatViewModel, onBackClick: () -> Unit) {

    val chatItemList by chatViewModel.chatItemsStateFlow.collectAsState()
    ChatScreen(chatItemList, chatViewModel.chatState, { event ->
        chatViewModel.onEvent(event)
    }, {
        onBackClick()
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    chatItemList: List<ChatItemModel<ChatData>>,
    chatState: ChatState,
    onEvent: (ChatEvent) -> Unit,
    onBackClick: () -> Unit
) {

    val snackBarHostState = remember { SnackbarHostState() }
    val listState = rememberLazyListState()

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar {
                onBackClick()
            }
        }, bottomBar = {
            BottomAppBar(chatState, {
                onEvent(ChatEvent.SendMessage)
                onEvent(ChatEvent.MessageChanged(""))
            }, {
                onEvent(ChatEvent.MessageChanged(it))
            })
        }) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            LazyColumn(
                contentPadding = PaddingValues(18.dp),
                state = listState
            ) {
                items(chatItemList.size) { index ->
                    val chatItem = chatItemList[index]
                    when (chatItem.chatItemType) {
                        ChatItemType.MESSAGE -> {
                            val messageModel = chatItem.data as ChatData.Message
                            when (messageModel.messageType) {
                                MessageType.SEND -> SenderBubbleMessageComposable(messageModel)
                                MessageType.RECEIVE -> ReceiverBubbleMessageComposable(messageModel)
                            }
                        }

                        ChatItemType.DATE -> {
                            val date = chatItem.data as ChatData.Date
                            MessageDateComposable(date)
                        }
                    }
                }
            }

            // Scroll to the last item when the message list changes
            LaunchedEffect(chatItemList) {
                listState.scrollToItem(chatItemList.lastIndex.takeIf { chatItemList.isNotEmpty() }
                    ?: 0)
            }
        }
    }
}


@Preview
@Composable
fun PreviewChatScreen() {
    ChatScreen(listOf(), ChatState(), {}, {})
}