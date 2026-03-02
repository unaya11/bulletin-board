package com.bulletinboard.etc

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class ThrowException {
    fun throwException() {
        if (Math.random() < 0.2) {
            throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "404 Not Found",
            )
        } else if (Math.random() < 0.4) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "400 Bad Request",
            )
        } else if (Math.random() < 0.8) {
            throw RuntimeException("RuntimeException !!!!!")
        }
    }
}
