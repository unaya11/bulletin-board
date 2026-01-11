package com.bulletinboard.service

import com.bulletinboard.data.Message
import com.bulletinboard.repository.MessageRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class MessageService(
    private val messageRepository: MessageRepository,
) {
    fun messageSave(
        id: Int,
        title: String,
        message: String,
    ) {
        messageRepository.save(
            Message(
                messageId = null,
                title = title,
                message = message,
                userId = id,
                createdAt = LocalDateTime.now(),
                user = null,
            ),
        )
    }
}
