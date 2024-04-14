package com.osamaaftab.muzz.data.repository.source.base

import com.osamaaftab.muzz.data.repository.entity.MessageEntity

interface ChatDataSource {
    suspend fun getChatMessages(): List<MessageEntity>
    suspend fun addMessage(message: MessageEntity)
    suspend fun getAutoReply(message: String, onReply: suspend (String?) -> Unit)
}