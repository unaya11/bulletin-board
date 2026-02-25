package com.bulletinboard.service

import com.bulletinboard.common.TestUtils.Companion.mockPage
import com.bulletinboard.common.TestUtils.Companion.testMessageDate
import com.bulletinboard.common.TestUtils.Companion.testMessageForm
import com.bulletinboard.common.TestUtils.Companion.testPage
import com.bulletinboard.common.TestUtils.Companion.testSearchKeyWord
import com.bulletinboard.common.TestUtils.Companion.testUser
import com.bulletinboard.data.Message
import com.bulletinboard.repository.MessageRepository
import com.bulletinboard.repository.UserRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.data.domain.Pageable
import kotlin.test.assertEquals

// https://qiita.com/gitcho/items/5ce478ec8e29bbddc152
@ExtendWith(MockitoExtension::class)
class MessageServiceTest {
    @Mock
    private lateinit var mockUserRepository: UserRepository

    @Mock
    private lateinit var mockMessageRepository: MessageRepository

    @InjectMocks
    private lateinit var messageService: MessageService

    @Test
    fun `すべてのメッセージが取得できること`() {
        whenever(mockMessageRepository.findAllMessage(any<Pageable>())).thenReturn(mockPage())
        messageService.findAll(testPage)

        verify(mockMessageRepository).findAllMessage(testPage)
    }

    @Test
    fun `すべての親メッセージが取得できること`() {
        whenever(mockMessageRepository.replyMessage(testMessageDate.messageId!!)).thenReturn(testMessageDate)
        messageService.findByParentMessage(testMessageDate.messageId)

        verify(mockMessageRepository).replyMessage(testMessageDate.messageId)
    }

    @Test
    fun `キーワード検索ができること`() {
        whenever(
            mockMessageRepository.findSearchMessage(eq(testSearchKeyWord), any<Pageable>()),
        ).thenReturn(mockPage())
        messageService.messageSearch(testPage, testSearchKeyWord)

        verify(mockMessageRepository).findSearchMessage(testSearchKeyWord, testPage)
    }

    @Test
    fun `メッセージが保存できること`() {
        val captor = argumentCaptor<Message>()
        messageService.messageSave(testUser, testMessageForm)

        verify(mockMessageRepository).save(captor.capture())
        assertEquals(testUser, captor.firstValue.user)
        assertEquals(testMessageDate.title, captor.firstValue.title)
        assertEquals(testMessageDate.message, captor.firstValue.message)
    }
}
