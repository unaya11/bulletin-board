package com.bulletinboard.controller

import com.bulletinboard.form.PostForm
import com.bulletinboard.form.ReplyForm
import com.bulletinboard.service.MessageService
import com.bulletinboard.service.ReplyService
import com.bulletinboard.service.UserService
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
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
        @ModelAttribute postForm: PostForm,
    ): String {
        val userId = userService.userCheck(postForm.name)
        messageService.messageSave(userId, postForm.title, postForm.message)
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
        @ModelAttribute replyForm: ReplyForm,
    ): String {
        val userId = userService.userCheck(replyForm.name)
        replyService.replySave(userId, replyForm.reply, messageId)
        return "redirect:/reply/$messageId"
    }

    @GetMapping("search")
    fun search(
        @RequestParam("keyword") keyword: String,
        model: Model,
        pageable: Pageable,
    ): String {
        val messageSearch = messageService.messageSearch(pageable, keyword)
        model.addAttribute("search", messageSearch)
        model.addAttribute("keyword", keyword)
        return "search"
    }
}
