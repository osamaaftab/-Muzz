package com.osamaaftab.muzz.domain.usecase

import com.osamaaftab.muzz.domain.model.MessageModel
import com.osamaaftab.muzz.domain.model.Resource
import com.osamaaftab.muzz.domain.repository.ChatRepository
import com.osamaaftab.muzz.domain.usecase.base.UseCase
import kotlinx.coroutines.flow.Flow

class SendMessageUseCase constructor(private val chatRepository: ChatRepository) :
    UseCase<MessageModel, MessageModel>() {

    override suspend fun run(params: MessageModel?): Flow<Resource<MessageModel>> {
        if (params == null) {
            throw IllegalArgumentException("Param must not be null")
        }
        return chatRepository.sendMessageAndGetReply(params)
    }
}