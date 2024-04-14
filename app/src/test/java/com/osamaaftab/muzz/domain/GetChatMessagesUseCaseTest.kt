package com.osamaaftab.muzz.domain

import com.osamaaftab.muzz.domain.model.ErrorModel
import com.osamaaftab.muzz.domain.model.MessageModel
import com.osamaaftab.muzz.domain.model.MessageType
import com.osamaaftab.muzz.domain.model.Resource
import com.osamaaftab.muzz.domain.repository.ChatRepository
import com.osamaaftab.muzz.domain.usecase.GetChatMessagesUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetChatMessagesUseCaseTest {

    // Mocked ChatRepository

    private val chatRepository: ChatRepository = mockk()

    // Instance of GetChatMessagesUseCase to be tested
    private lateinit var sut: GetChatMessagesUseCase

    @Before
    fun setUp() {
        // Initialize Mockito annotations
        MockitoAnnotations.initMocks(this)

        // Initialize the GetChatMessagesUseCase with the mocked ChatRepository
        sut = GetChatMessagesUseCase(chatRepository)
    }

    @Test
    fun `getChatMessages should emit success`() = runBlocking {
        // Mocked list of MessageModel
        val mockedMessageList = listOf(
            MessageModel(MessageType.SEND, "Hello", "12:00"),
        )

        // Mock behavior of getChatMessages() in the chatRepository
        coEvery { chatRepository.getChatMessages() } returns flowOf(
            Resource.Success(
                mockedMessageList
            )
        )


        // Call the run function and collect the emitted value
        val resultFlow = sut.run().toList()

        // Verify the emitted value is a Resource.Success containing the expected message list
        assertEquals(1, resultFlow.size)
        val result = resultFlow.first()
        assert(result is Resource.Success)
        val messageList = (result as Resource.Success).data
        assertEquals(mockedMessageList, messageList)
    }


    @Test
    fun `getChatMessages should emit failure`() = runBlocking {
        // Mocked list of MessageModel
        val mockedMessageList = listOf(
            MessageModel(MessageType.SEND, "Hello", "12:00"),
        )

        // Mock behavior of getChatMessages() in the chatRepository
        coEvery { chatRepository.getChatMessages() } returns flowOf(
            Resource.Error(
                errorModel = ErrorModel(
                    "Error message",
                    ErrorModel.ErrorStatus.NOT_DEFINED
                )
            )
        )


        // Call the run function and collect the emitted value
        val resultFlow = sut.run().toList()

        assertEquals(1, resultFlow.size)
        val result = resultFlow.first()

        assertTrue(result is Resource.Error)

        // Optionally, verify the error message or error model
        val errorModel = result.errorModel
        assertEquals("Error message", errorModel?.message)
        assertEquals(ErrorModel.ErrorStatus.NOT_DEFINED, errorModel?.errorStatus)
    }
}