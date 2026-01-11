package com.bulletinboard.repository

import com.bulletinboard.data.Reply
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ReplyRepository : JpaRepository<Reply, Int> {
    @Query("SELECT r FROM Reply r LEFT JOIN r.user u WHERE r.messageId =:messageId")
    fun findByReplyMessage(messageId: Int): List<Reply>
}
