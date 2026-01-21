package com.bulletinboard.controller

import com.bulletinboard.service.MessageService
import com.bulletinboard.service.ReplyService
import com.bulletinboard.service.UserService
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@RequestMapping("/")
@Controller
class BulletinBoardController(
    private val userService: UserService,
    private val messageService: MessageService,
    private val replyService: ReplyService,
) {
    @GetMapping("top")
    fun viewTop(
        model: Model,
        pageable: Pageable,
    ): String {
        val message = messageService.findAll(pageable)
        model.addAttribute("message", message)
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

    @GetMapping("reply/{messageId}")
    fun replyPage(
        @PathVariable messageId: Int,
        model: Model,
        pageable: Pageable,
    ): String {
        val replyMessage = replyService.findByReplyMessage(pageable, messageId)
        model.addAttribute("parentMessage", messageService.findByParentMessage(messageId))
        model.addAttribute("replyMessage", replyMessage)
        return "reply"
    }

    @PostMapping("reply/{messageId}")
    fun postReply(
        @PathVariable messageId: Int,
        @RequestParam name: String,
        @RequestParam reply: String,
    ): String {
        val userId = userService.userCheck(name)
        replyService.replySave(userId, reply, messageId)
        return "redirect:/reply/$messageId"
    }
}
