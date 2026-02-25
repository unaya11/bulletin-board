package com.bulletinboard.common

import com.bulletinboard.data.Message
import com.bulletinboard.data.Reply
import com.bulletinboard.data.User
import com.bulletinboard.form.MessageForm
import com.bulletinboard.form.ReplyForm
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import java.time.LocalDateTime

class TestUtils {
    companion object {
        val testSearchKeyWord = "keyWord"
        val testUser = User(100, "testName")
        val testPage: Pageable = PageRequest.of(1, 2)
        val testMessageDate =
            Message(
                messageId = 200,
                title = "testTitle",
                message = "testMessage",
                createdAt = LocalDateTime.now(),
                user = testUser,
                replies = mutableListOf(),
            )

        val mockReply1 =
            Reply(
                replyId = 1,
                reply = "testReply1",
                createdAt = LocalDateTime.now(),
                message = testMessageDate,
                user = testUser,
            )
        val mockReply2 =
            Reply(
                replyId = 2,
                reply = "testReply2",
                createdAt = LocalDateTime.now(),
                message = testMessageDate,
                user = testUser,
            )

        fun <T : Any> mockPage(): Page<T> = PageImpl(emptyList(), PageRequest.of(0, 1), 0)

        fun originalPage(): PageImpl<Reply> = PageImpl(listOf(mockReply1, mockReply2), PageRequest.of(0, 1), 0)

        val testMessageForm: MessageForm =
            MessageForm(
                name = testUser.name,
                title = testMessageDate.title,
                message = testMessageDate.message,
            )
        val testReplyForm: ReplyForm =
            ReplyForm(
                testUser.name,
                testMessageDate.message,
            )
    }
}
