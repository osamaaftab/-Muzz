package com.osamaaftab.muzz.domain

import com.osamaaftab.muzz.domain.model.MessageModel
import com.osamaaftab.muzz.domain.model.MessageType
import com.osamaaftab.muzz.domain.model.Resource
import com.osamaaftab.muzz.domain.repository.ChatRepository
import com.osamaaftab.muzz.domain.usecase.SendMessageUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith


class SendMessageUseCaseTest {

    private val chatRepository: ChatRepository = mockk()
    private val sut = SendMessageUseCase(chatRepository)


    @Test
    fun `SendMessage should emit success`() = runBlocking {
        // Arrange
        val sendMessageModel = MessageModel(MessageType.SEND, "Message goes here..")
        val receiveMessageModel = MessageModel(MessageType.RECEIVE, "reply goes here..")
        coEvery { chatRepository.sendMessageAndGetReply(sendMessageModel) } returns flowOf(
            Resource.Success(
                receiveMessageModel
            )
        )

        // Act
        val resultFlow = sut.run(sendMessageModel).toList()

        // Assert
        assertEquals(1, resultFlow.size)
        val result = resultFlow.first()
        assert(result is Resource.Success)
        val reply = (result as Resource.Success).data
        assertEquals(receiveMessageModel, reply)
    }

    @Test
    fun `run should throw IllegalArgumentException for null params`() {
        // Arrange
        val params: MessageModel? = null
        val messageModel = mockk<MessageModel>(relaxed = true)

        // Mock behavior of currencyRepository.getLatestRates()
        coEvery { chatRepository.sendMessageAndGetReply(messageModel) } returns flowOf(
            Resource.Success(
                messageModel
            )
        )

        // Act & Assert
        assertFailsWith<IllegalArgumentException> {
            runBlocking {
                sut.run(params).collect()
            }
        }

        // Verify that currencyRepository.getLatestRates() was not called
        coVerify(exactly = 0) { chatRepository.sendMessageAndGetReply(messageModel) }
    }
}