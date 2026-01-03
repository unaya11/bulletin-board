package com.bulletinboard.repository

import com.bulletinboard.data.Message
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface MessageRepository : JpaRepository<Message, Int> {
    // SELECT * FROM message AS m LEFT JOIN "user" AS u ON m.user_id = u.user_Id
//    @Query("SELECT m FROM Message AS m LEFT JOIN User AS u ON m.userid = u.userId")
    @Query("SELECT m FROM Message m LEFT JOIN m.user u")
    fun findMessage(): List<Message>
}
