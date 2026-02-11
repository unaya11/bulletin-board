package com.bulletinboard.common

import com.bulletinboard.data.Message
import com.bulletinboard.data.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import java.time.LocalDateTime

class TestUtils {
    companion object {
        val testId = 100
        val testName = "testName"
        val testTitle = "testTitle"
        val testMessage = "testMessage"
        val testMessageId = 200
        val testUser = User()
        val testMessageDate =
            Message(
                messageId = testMessageId,
                title = testTitle,
                message = testMessage,
                createdAt = LocalDateTime.now(),
                user = testUser,
                replies = mutableListOf(),
            )

        fun <T : Any> mockPage(): Page<T> = PageImpl(emptyList(), PageRequest.of(0, 1), 0)
    }
}
