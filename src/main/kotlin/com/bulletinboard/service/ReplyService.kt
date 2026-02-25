package com.bulletinboard.service

import com.bulletinboard.data.Reply
import com.bulletinboard.data.User
import com.bulletinboard.form.ReplyForm
import com.bulletinboard.repository.MessageRepository
import com.bulletinboard.repository.ReplyRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ReplyService(
    private val replyRepository: ReplyRepository,
    private val messageRepository: MessageRepository,
) {
    fun findByReplyMessage(
        pageable: Pageable,
        messageId: Int,
    ): Page<Reply> {
        val replyMessage = replyRepository.findByReplyMessage(pageable, messageId)
        return reverseContent(replyMessage)
    }

    fun reverseContent(originalPage: Page<Reply>): Page<Reply> {
        val reverseReply = originalPage.content.asReversed()
        val reversedList = PageImpl(reverseReply, originalPage.pageable, originalPage.totalElements)
        return reversedList
    }

    fun replySave(
        user: User,
        replyForm: ReplyForm,
        messageId: Int,
    ) {
        replyRepository.save(
            Reply(
                reply = replyForm.reply,
                message = messageRepository.getReferenceById(messageId),
                user = user,
            ),
        )
    }
}
