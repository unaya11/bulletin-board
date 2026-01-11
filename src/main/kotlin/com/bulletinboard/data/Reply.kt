package com.bulletinboard.data

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "reply")
data class Reply(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val replyId: Int?,
    @Column(nullable = false)
    val reply: String = "",
    @Column(name = "user_id", nullable = false)
    var userId: Int?,
    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime?,
    @Column(name = "message_id", nullable = false)
    var messageId: Int?,
    @ManyToOne
    @JoinColumn(name = "message_id", insertable = false, updatable = false, nullable = false)
    val message: Message?,
    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false, nullable = false)
    val user: User?,
)
