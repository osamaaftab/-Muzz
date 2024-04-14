package com.osamaaftab.muzz.presentation.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.osamaaftab.muzz.common.Util.getCurrentTimeFormatted
import com.osamaaftab.muzz.domain.model.ChatData
import com.osamaaftab.muzz.domain.model.ChatItemModel
import com.osamaaftab.muzz.domain.model.ChatItemType
import com.osamaaftab.muzz.domain.model.ErrorModel
import com.osamaaftab.muzz.domain.model.MessageModel
import com.osamaaftab.muzz.domain.model.MessageType
import com.osamaaftab.muzz.domain.usecase.GetChatMessagesUseCase
import com.osamaaftab.muzz.domain.usecase.SendMessageUseCase
import com.osamaaftab.muzz.presentation.event.ChatEvent
import com.osamaaftab.muzz.presentation.state.ChatState
import io.realm.kotlin.internal.platform.runBlocking
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ChatViewModel(
    private val getChatMessagesUseCase: GetChatMessagesUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
) : ViewModel() {

    private val chatItemsDataState = MutableStateFlow<List<ChatItemModel<ChatData>>>(listOf())

    private var chatItemList = chatItemsDataState.value.toMutableList()

    var chatState by mutableStateOf(ChatState())

    init {
        loadChatHistory()
    }

    fun onEvent(event: ChatEvent) {
        when (event) {

            is ChatEvent.MessageChanged -> {
                chatState = chatState.copy(message = event.message)
            }

            is ChatEvent.SendMessage -> {
                sendMessage(message = chatState.message)
            }
        }
    }

    private fun getChatMessagesSuccess(responseModel: List<MessageModel>) {
        Log.i(ContentValues.TAG, "result : $responseModel")

        responseModel.map { messageModel ->
            addDateItemIfNeeded(messageModel.time.toLong(), messageModel.time.toLong())
            chatItemList.add(
                ChatItemModel(
                    ChatItemType.MESSAGE,
                    ChatData.Message(
                        messageModel.messageType,
                        messageModel.messageContent,
                        messageModel.time
                    )
                )
            )
        }

        if (chatItemList.isEmpty()) {
            chatItemList.add(
                ChatItemModel(
                    ChatItemType.DATE,
                    ChatData.Date(getCurrentTimeFormatted(System.currentTimeMillis()))
                )
            )
        }

        chatItemsDataState.value = chatItemList
    }

    private fun getChatMessagesFails(errorModel: ErrorModel?) {
        Log.i(ContentValues.TAG, "error status: ${errorModel?.errorStatus}")
        Log.i(ContentValues.TAG, "error message: ${errorModel?.message}")
    }

    fun loadChatHistory() {
        getChatMessagesUseCase.invoke(viewModelScope, null, onSuccess = {
            getChatMessagesSuccess(it)
        }, onError = {
            getChatMessagesFails(it)
        })
    }

    private fun sendMessage(message: String) {
        val messageModel = MessageModel(MessageType.SEND, message)
        addMessageToUiList(messageModel)
        sendMessageUseCase.invoke(viewModelScope, messageModel,
            onSuccess = {
                Log.i(ContentValues.TAG, "successfully inserted and got a reply")
                runBlocking {
                    delay(1000)
                    addMessageToUiList(it)
                }
            }, onError = {
                Log.i(ContentValues.TAG, "error status: ${it?.errorStatus}")
            })
    }

    private fun addMessageToUiList(message: MessageModel) {
        val lastMessageTime = getLastMessageTime()

        // Add a date item if necessary
        addDateItemIfNeeded(lastMessageTime, System.currentTimeMillis())

        // Add the new message item directly to the existing list
        val newChatItemList = chatItemsDataState.value.toMutableList()
        newChatItemList.add(
            ChatItemModel(
                ChatItemType.MESSAGE,
                ChatData.Message(message.messageType, message.messageContent, message.time)
            )
        )

        // Update the state flow and local list.
        chatItemsDataState.value = newChatItemList
        chatItemList = newChatItemList
    }

    private fun getLastMessageTime(): Long? {

        val lastChatItemModel = chatItemList.lastOrNull { it.chatItemType == ChatItemType.MESSAGE }
        return if (lastChatItemModel != null) {
            val lastMessageTime = lastChatItemModel.data as ChatData.Message
            lastMessageTime.time.toLong()
        } else {
            null
        }
    }

    private fun addDateItemIfNeeded(lastMessageTime: Long?, timeToShow: Long) {

        if (lastMessageTime != null) {
            val oneHourInMillis = 60 * 60 * 1000 // 1 hour in milliseconds
            val currentTimeMillis = System.currentTimeMillis()

            if (currentTimeMillis - lastMessageTime > oneHourInMillis) {
                chatItemList.add(
                    ChatItemModel(
                        ChatItemType.DATE,
                        ChatData.Date(getCurrentTimeFormatted(timeToShow))
                    )
                )
            }
        }
    }

    val chatItemsStateFlow: StateFlow<List<ChatItemModel<ChatData>>> = chatItemsDataState
}