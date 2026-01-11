package com.bulletinboard.service

import com.bulletinboard.data.Reply
import com.bulletinboard.repository.MessageRepository
import com.bulletinboard.repository.ReplyRepository
import com.bulletinboard.repository.UserRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ReplyService(
    private val replyRepository: ReplyRepository,
    private val messageRepository: MessageRepository,
    private val userRepository: UserRepository,
) {
    fun findByReplyMessage(messageId: Int): List<Reply> = replyRepository.findByReplyMessage(messageId)

    fun replySave(
        userId: Int,
        reply: String,
        messageId: Int,
    ) {
        replyRepository.save(
            Reply(
                replyId = null,
                reply = reply,
                createdAt = null,
                message = messageRepository.getReferenceById(messageId),
                user = userRepository.getReferenceById(userId),
            ),
        )
    }
}
