package com.bulletinboard.repository

import com.bulletinboard.data.Reply
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ReplyRepository : JpaRepository<Reply, Int> {
    @Query("SELECT r FROM Reply r LEFT JOIN FETCH r.user u WHERE r.message.messageId =:messageId ORDER BY r.createdAt DESC")
    fun findByReplyMessage(
        pageable: Pageable,
        messageId: Int,
    ): Page<Reply>
}
