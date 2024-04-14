package com.osamaaftab.muzz.data.repository.source

import com.osamaaftab.muzz.data.repository.ChatRepositoryImp
import com.osamaaftab.muzz.data.repository.ErrorHandle
import com.osamaaftab.muzz.data.repository.entity.MessageEntity
import com.osamaaftab.muzz.data.repository.source.local.ChatLocalDataSource
import com.osamaaftab.muzz.domain.model.ErrorModel
import com.osamaaftab.muzz.domain.model.MessageModel
import com.osamaaftab.muzz.domain.model.MessageType
import com.osamaaftab.muzz.domain.model.Resource
import com.osamaaftab.muzz.domain.repository.ChatRepository
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class ChatRepositoryImpTest {

    private lateinit var sut: ChatRepository
    private lateinit var localDataSource: ChatLocalDataSource
    private lateinit var errorHandle: ErrorHandle


    @Before
    fun setup() {
        localDataSource = mockk()
        errorHandle = mockk()
        sut = ChatRepositoryImp(localDataSource, errorHandle)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `test getChatMessages returns success`() = runBlocking {
        // Mock data
        val messageEntityList = listOf(
            MessageEntity("1", MessageType.SEND.name, "Message 1", "Time 1"),
            MessageEntity("2", MessageType.SEND.name, "Message 2", "Time 2")
        )
        val expectedMessageModelList = listOf(
            MessageModel(MessageType.SEND, "Message 1", "Time 1"),
            MessageModel(MessageType.SEND, "Message 2", "Time 2")
        )
        // Stub localDataSource.getChatMessages
        coEvery { localDataSource.getChatMessages() } returns messageEntityList


        // Call the repository function
        val flow = sut.getChatMessages()

        // Verify the emitted values
        // Collect the flow and store emitted values in a list
        val collectedValues = mutableListOf<Resource<List<MessageModel>>>()
        flow.collect { resource ->
            collectedValues.add(resource)
        }

        // Perform assertions on the collected values
        val resource = collectedValues.first()
        assert(resource is Resource.Success)
        val messageList = (resource as Resource.Success).data
        assertEquals(messageList, expectedMessageModelList)
    }

    @Test
    fun `test getChatMessages returns error`() {
        // Mock the local data source to throw an exception when getChatMessages is called
        val mockLocalDataSource = mockk<ChatLocalDataSource>()

        // Mock the ErrorHandle class
        val errorHandle = mockk<ErrorHandle>()

        coEvery { mockLocalDataSource.getChatMessages() } throws Exception()

        every { errorHandle.traceErrorException(any()) } returns ErrorModel(
            "Error message",
            ErrorModel.ErrorStatus.NOT_DEFINED
        )

        // Create an instance of the repository with the mocked local data source
        val repository = ChatRepositoryImp(mockLocalDataSource, errorHandle)

        // Call the function under test within a coroutine
        runBlocking {
            // Collect the flow emitted by getChatMessages
            repository.getChatMessages().collect { resource ->
                // Verify that the resource is an error
                assertTrue(resource is Resource.Error)

                // Optionally, verify the error message or error model
                val errorModel = resource.errorModel
                assertEquals("Error message", errorModel?.message)
                assertEquals(ErrorModel.ErrorStatus.NOT_DEFINED, errorModel?.errorStatus)
            }
        }
    }
}