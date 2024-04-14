package com.osamaaftab.muzz.data.repository.source.local

import com.google.mlkit.nl.smartreply.SmartReplyGenerator
import com.google.mlkit.nl.smartreply.SmartReplySuggestionResult
import com.google.mlkit.nl.smartreply.TextMessage
import com.osamaaftab.muzz.common.Util
import com.osamaaftab.muzz.data.repository.entity.MessageEntity
import com.osamaaftab.muzz.data.repository.source.base.ChatDataSource
import io.realm.kotlin.Realm
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.UUID
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class ChatLocalDataSource(
    private val realm: Realm,
    private val smartReplyGenerator: SmartReplyGenerator
) : ChatDataSource {

    override suspend fun getChatMessages(): List<MessageEntity> {
        return realm.query(MessageEntity::class).find()
    }

    override suspend fun addMessage(message: MessageEntity) {
        return realm.writeBlocking {
            copyToRealm(message)
        }
    }

    override suspend fun getAutoReply(message: String, onReply: suspend (String?) -> Unit) {

        val conversation: ArrayList<TextMessage> = ArrayList()

        conversation.add(
            TextMessage.createForRemoteUser(
                message, System.currentTimeMillis(), UUID.randomUUID().toString()
            )
        )

        try {
            val result = suspendCancellableCoroutine<SmartReplySuggestionResult?> { continuation ->
                smartReplyGenerator.suggestReplies(conversation)
                    .addOnSuccessListener { result ->
                        continuation.resume(result) // Resume the coroutine with the result
                    }
                    .addOnFailureListener { exception ->
                        // Handle the failure case
                        if (!continuation.isCancelled) {
                            continuation.resumeWithException(exception) // Resume with an exception if there's a failure
                        }
                    }
            }

            if (result?.status == SmartReplySuggestionResult.STATUS_NOT_SUPPORTED_LANGUAGE) {
                onReply(null) // Handle the case where language is not supported
            } else if (result?.status == SmartReplySuggestionResult.STATUS_SUCCESS) {
                val randomReply = Util.getRandomItem(result.suggestions)?.text
                onReply(randomReply) // Pass the result to the callback
            }
        } catch (e: Exception) {
            throw e // Rethrow the exception if there's a failure
        }
    }
}