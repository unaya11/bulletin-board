package com.bulletinboard.service

import com.bulletinboard.data.Message
import com.bulletinboard.repository.MessageRepository
import com.bulletinboard.repository.UserRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class MessageService(
    private val messageRepository: MessageRepository,
    private val userRepository: UserRepository
) {
    fun findAll(): List<Message> = messageRepository.findAllMessage()

    fun findByParentMessage(messageId: Int): Message = messageRepository.replyMessage(messageId)

    fun messageSave(
        userId: Int,
        title: String,
        message: String,
    ) {
        messageRepository.save(
            Message(
                messageId = null,
                title = title,
                message = message,
                user = userRepository.getReferenceById(userId),
                createdAt = LocalDateTime.now(),
            ),
        )
    }
}
