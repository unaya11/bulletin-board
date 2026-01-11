package com.bulletinboard.data

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@Table(name = "reply")
@EntityListeners(AuditingEntityListener::class)
data class Reply(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val replyId: Int?,
    @Column(nullable = false)
    val reply: String = "",
    @Column(name = "created_at", nullable = false)
    @CreatedDate
    var createdAt: LocalDateTime?,
    @ManyToOne
    @JoinColumn(name = "message_id", nullable = false)
    var message: Message? = null,
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User?,
)
