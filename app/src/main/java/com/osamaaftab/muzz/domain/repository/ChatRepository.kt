package com.osamaaftab.muzz.domain.repository

import com.osamaaftab.muzz.domain.model.MessageModel
import com.osamaaftab.muzz.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun getChatMessages(): Flow<Resource<List<MessageModel>>>
    suspend fun sendMessageAndGetReply(message: MessageModel): Flow<Resource<MessageModel>>
}