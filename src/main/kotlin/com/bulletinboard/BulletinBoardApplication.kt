package com.bulletinboard

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class BulletinBoardApplication

fun main(args: Array<String>) {
    runApplication<BulletinBoardApplication>(*args)
}
