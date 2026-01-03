package com.bulletinboard.data

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "message")
data class Message(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val messageId: Int?,
    @Column(length = 100, nullable = false)
    val title: String = "",
    @Column(nullable = false)
    val message: String = "",
    @Column(name = "user_id", nullable = false)
    var userId: Int?,
    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false, nullable = false)
    val user: User?,
)
