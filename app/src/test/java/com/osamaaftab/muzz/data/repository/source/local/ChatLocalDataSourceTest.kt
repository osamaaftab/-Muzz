package com.osamaaftab.muzz.data.repository.source.local

import com.google.mlkit.nl.smartreply.SmartReplyGenerator
import com.osamaaftab.muzz.data.repository.entity.MessageEntity
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.realm.kotlin.Realm
import io.realm.kotlin.internal.platform.runBlocking
import io.realm.kotlin.query.RealmResults
import org.junit.After
import org.junit.Before
import org.junit.Test

class ChatLocalDataSourceTest {

    // Mocks
    private val realm = mockk<Realm>(relaxed = true)

    @MockK
    lateinit var smartReplyGenerator: SmartReplyGenerator

    // System under test
    private lateinit var sut: ChatLocalDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        sut = ChatLocalDataSource(realm, smartReplyGenerator)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `test getChatMessages returns expected result`() {
        // Given
        val realmResults: RealmResults<MessageEntity> = mockk()

        // Mock the behavior to return the desired list of message entities
        every { realm.query(MessageEntity::class).find() } returns realmResults

        // When
        val result = runBlocking { sut.getChatMessages() }

        // Then
        assert(result == realmResults)
    }

    @Test
    fun `test addMessage calls realm writeBlocking`() {
        //Given
        val messageEntity = mockk<MessageEntity>()

        // Mock realm.writeBlocking to do nothing
        coEvery { realm.writeBlocking { any<() -> Unit>() } } coAnswers { { } }

        // When
        runBlocking { sut.addMessage(messageEntity) }

        // Then
        coEvery { realm.writeBlocking { copyToRealm(messageEntity) } }
    }
}