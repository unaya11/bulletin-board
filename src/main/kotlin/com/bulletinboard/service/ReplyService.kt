package com.bulletinboard.service

import com.bulletinboard.data.Reply
import com.bulletinboard.repository.ReplyRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ReplyService(
    private val replyRepository: ReplyRepository,
) {
    fun replySave(
        userId: Int,
        reply: String,
        messageId: Int,
    ) {
        replyRepository.save(
            Reply(
                replyId = null,
                reply = reply,
                userId = userId,
                createdAt = LocalDateTime.now(),
                messageId = messageId,
                message = null,
                user = null,
            ),
        )
    }
}
