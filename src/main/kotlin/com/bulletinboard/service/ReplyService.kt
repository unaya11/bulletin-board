package com.bulletinboard.service

import com.bulletinboard.data.Reply
import com.bulletinboard.repository.MessageRepository
import com.bulletinboard.repository.ReplyRepository
import com.bulletinboard.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ReplyService(
    private val replyRepository: ReplyRepository,
    private val messageRepository: MessageRepository,
    private val userRepository: UserRepository,
) {
    fun findByReplyMessage(
        pageable: Pageable,
        messageId: Int,
    ): Page<Reply> = replyRepository.findByReplyMessage(pageable, messageId)

    fun replySave(
        userId: Int,
        reply: String,
        messageId: Int,
    ) {
        replyRepository.save(
            Reply(
                reply = reply,
                message = messageRepository.getReferenceById(messageId),
                user = userRepository.getReferenceById(userId),
            ),
        )
    }
}
