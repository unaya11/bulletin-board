package com.bulletinboard.service

import com.bulletinboard.data.Message
import com.bulletinboard.data.User
import com.bulletinboard.form.MessageForm
import com.bulletinboard.repository.MessageRepository
import com.bulletinboard.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class MessageService(
    private val messageRepository: MessageRepository,
) {
    fun findAll(pageable: Pageable): Page<Message> = messageRepository.findAllMessage(pageable)

    fun findByParentMessage(messageId: Int): Message = messageRepository.replyMessage(messageId)

    fun messageSearch(
        pageable: Pageable,
        keyword: String,
    ): Page<Message> = messageRepository.findSearchMessage(keyword, pageable)

    fun messageSave(
        user: User,
        messageForm: MessageForm,
    ) {
        messageRepository.save(
            Message(
                user = user,
                title = messageForm.title,
                message = messageForm.message,
            ),
        )
    }
}
