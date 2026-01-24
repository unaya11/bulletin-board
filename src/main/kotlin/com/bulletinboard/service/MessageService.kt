package com.bulletinboard.service

import com.bulletinboard.data.Message
import com.bulletinboard.repository.MessageRepository
import com.bulletinboard.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class MessageService(
    private val messageRepository: MessageRepository,
    private val userRepository: UserRepository,
) {
    fun findAll(pageable: Pageable): Page<Message> = messageRepository.findAllMessage(pageable)

    fun findByParentMessage(messageId: Int): Message = messageRepository.replyMessage(messageId)

    fun messageSearch(
        keyword: String,
        pageable: Pageable,
    ): Page<Message> = messageRepository.findSearchMessage(keyword, pageable)

    fun messageSave(
        userId: Int,
        title: String,
        message: String,
    ) {
        messageRepository.save(
            Message(
                title = title,
                message = message,
                user = userRepository.getReferenceById(userId),
            ),
        )
    }
}
