package com.bulletinboard.repository

import com.bulletinboard.data.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Int> {
    fun findByName(name: String): User?
}
