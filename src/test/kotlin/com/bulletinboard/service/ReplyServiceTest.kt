package com.bulletinboard.service

import com.bulletinboard.common.TestUtils.Companion.mockReply1
import com.bulletinboard.common.TestUtils.Companion.mockReply2
import com.bulletinboard.common.TestUtils.Companion.originalPage
import com.bulletinboard.common.TestUtils.Companion.testMessageDate
import com.bulletinboard.common.TestUtils.Companion.testPage
import com.bulletinboard.common.TestUtils.Companion.testReplyForm
import com.bulletinboard.common.TestUtils.Companion.testUser
import com.bulletinboard.data.Reply
import com.bulletinboard.repository.MessageRepository
import com.bulletinboard.repository.ReplyRepository
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

@ExtendWith(MockitoExtension::class)
class ReplyServiceTest {
    @Mock
    private lateinit var mockUserRepository: UserRepository

    @Mock
    private lateinit var mockMessageRepository: MessageRepository

    @Mock
    private lateinit var mockReplyRepository: ReplyRepository

    @InjectMocks
    private lateinit var replyService: ReplyService

    @Test
    fun `返信が取得でき、中身が反転されていること`() {
        whenever(
            mockReplyRepository.findByReplyMessage(any<Pageable>(), eq(testMessageDate.messageId!!)),
        ).thenReturn(originalPage())
        val result = replyService.findByReplyMessage(testPage, testMessageDate.messageId!!)
        assertEquals(mockReply1, result.content[1])
        assertEquals(mockReply2, result.content[0])
    }

    @Test
    fun `返信が保存できること`() {
        val captor = argumentCaptor<Reply>()
        whenever(mockMessageRepository.getReferenceById(testMessageDate.messageId!!)).thenReturn(testMessageDate)
        whenever(mockReplyRepository.save(any())).thenReturn(mockReply1)
        replyService.replySave(
            testUser,
            testReplyForm,
            testMessageDate.messageId!!,
        )
        verify(mockReplyRepository).save(captor.capture())
        assertEquals(mockReply1.reply, captor.firstValue.reply)
        assertEquals(mockReply1.message, captor.firstValue.message)
        assertEquals(mockReply1.user, captor.firstValue.user)
    }
}
