package com.osamaaftab.muzz.domain.usecase

import com.osamaaftab.muzz.domain.model.MessageModel
import com.osamaaftab.muzz.domain.model.Resource
import com.osamaaftab.muzz.domain.repository.ChatRepository
import com.osamaaftab.muzz.domain.usecase.base.UseCase
import kotlinx.coroutines.flow.Flow

class GetChatMessagesUseCase

constructor(private val chatRepository: ChatRepository) :
    UseCase<List<MessageModel>, Unit>() {

    override suspend fun run(params: Unit?): Flow<Resource<List<MessageModel>>> {
        return chatRepository.getChatMessages()
    }
}