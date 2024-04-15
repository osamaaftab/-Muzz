package com.osamaaftab.muzz.data.repository

import com.osamaaftab.muzz.data.repository.entity.MessageEntity
import com.osamaaftab.muzz.data.repository.source.local.ChatLocalDataSource
import com.osamaaftab.muzz.domain.model.ErrorModel
import com.osamaaftab.muzz.domain.model.MessageModel
import com.osamaaftab.muzz.domain.model.MessageType
import com.osamaaftab.muzz.domain.model.Resource
import com.osamaaftab.muzz.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow

class ChatRepositoryImp(
    private val localDataSource: ChatLocalDataSource,
    private val errorHandle: ErrorHandle
) : ChatRepository {


    override suspend fun getChatMessages(): Flow<Resource<List<MessageModel>>> = flow {
        val result = runCatching {
            localDataSource.getChatMessages().map {
                MessageModel(
                    messageType = MessageType.valueOf(it.messageType),
                    messageContent = it.messageContent,
                    time = it.time
                )
            }
        }
        emit(result.fold(
            onSuccess = { Resource.Success(it) },
            onFailure = { Resource.Error(errorModel = errorHandle.traceErrorException(it)) }
        ))
    }

    override suspend fun sendMessageAndGetReply(message: MessageModel): Flow<Resource<MessageModel>> =
        flow {
            try {
                addMessageToLocalStorage(
                    MessageEntity(
                        messageType = message.messageType.name,
                        messageContent = message.messageContent,
                        time = message.time
                    )
                )
                localDataSource.getAutoReply(message.messageContent) { reply ->
                    saveReplyAndEmit(reply)
                }
            } catch (e: Exception) {
                emit(Resource.Error(errorModel = errorHandle.traceErrorException(e)))
            }
        }

    private suspend fun FlowCollector<Resource<MessageModel>>.saveReplyAndEmit(reply: String?) {
        if (reply != null) {
            val messageEntity =
                MessageEntity(messageType = MessageType.RECEIVE.name, messageContent = reply)
            addMessageToLocalStorage(messageEntity)
            val messageModel = MessageModel(
                messageType = MessageType.valueOf(messageEntity.messageType),
                messageContent = messageEntity.messageContent,
                time = messageEntity.time
            )
            emit(Resource.Success(messageModel))
        } else {
            emit(
                Resource.Error(
                    errorModel = ErrorModel(
                        "Error in getting reply",
                        ErrorModel.ErrorStatus.NOT_DEFINED
                    )
                )
            )
        }
    }

    private suspend fun addMessageToLocalStorage(messageModel: MessageEntity) {
        localDataSource.addMessage(messageModel)
    }
}