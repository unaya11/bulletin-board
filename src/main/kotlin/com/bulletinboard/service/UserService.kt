package com.bulletinboard.service

import com.bulletinboard.data.User
import com.bulletinboard.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    fun userCheck(name: String): Int {
        val user =
            userRepository.findByName(name) ?: userRepository.save(
                User(
                    name = name,
                ),
            )
        return user.userId!!
    }
}
