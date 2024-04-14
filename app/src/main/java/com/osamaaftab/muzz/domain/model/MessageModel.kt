package com.osamaaftab.muzz.domain.model

data class MessageModel(
    val messageType: MessageType,
    val messageContent: String,
    val time: String = System.currentTimeMillis().toString()
)