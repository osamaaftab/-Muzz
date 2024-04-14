package com.osamaaftab.muzz.domain.model

sealed class ChatData {
    data class Message(
        val messageType: MessageType,
        val messageContent: String,
        val time: String,
    ) : ChatData()

    data class Date(val value: String) : ChatData()
}

enum class ChatItemType {
    DATE, MESSAGE,
}