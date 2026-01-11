package com.bulletinboard.data

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "user")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userId: Int?,
    @Column(length = 100, nullable = false)
    val name: String = "",
    @Column(length = 255, nullable = false)
    val email: String = "",
)
