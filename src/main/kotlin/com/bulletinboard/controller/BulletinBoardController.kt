package com.bulletinboard.controller

import com.bulletinboard.data.Message
import com.bulletinboard.repository.MessageRepository
import com.bulletinboard.repository.UserRepository
import com.bulletinboard.service.MessageService
import com.bulletinboard.service.UserService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@RequestMapping("/")
@Controller
class BulletinBoardController(
    private val userRepository: UserRepository,
    private val messageRepository: MessageRepository,
    private val userService: UserService,
    private val messageService: MessageService,
) {
    @GetMapping("top")
    fun viewTop(model: Model): String {
        model.addAttribute("message", messageRepository.findMessage())
        return "top"
    }

    @PostMapping("top")
    fun postForm(
        @RequestParam name: String,
        @RequestParam title: String,
        @RequestParam message: String,
    ): String {
        val userId = userService.userCheck(name)
        messageService.messageSave(userId, title, message)
        return "redirect:/top"
    }
}
