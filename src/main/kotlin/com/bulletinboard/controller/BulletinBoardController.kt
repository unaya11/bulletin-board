package com.bulletinboard.controller

import com.bulletinboard.etc.CustomRetryConfig
import com.bulletinboard.etc.ThrowException
import com.bulletinboard.form.MessageForm
import com.bulletinboard.form.ReplyForm
import com.bulletinboard.service.MessageService
import com.bulletinboard.service.ReplyService
import com.bulletinboard.service.UserService
import jakarta.validation.Valid
import org.springframework.core.retry.RetryTemplate
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.server.ResponseStatusException

@RequestMapping("/")
@Controller
class BulletinBoardController(
    private val userService: UserService,
    private val messageService: MessageService,
    private val replyService: ReplyService,
    private val retryConfig: CustomRetryConfig,
) {
    @GetMapping("top")
    fun viewTop(
        @ModelAttribute messageForm: MessageForm,
        model: Model,
        pageable: Pageable,
    ): String {
        try {
            retryConfig.a().execute {
                ThrowException().throwException()
            }
        } catch (e: ResponseStatusException) {
        }

        val message = messageService.findAll(pageable)
        model.addAttribute("message", message)
        return "top"
    }

    @PostMapping("top")
    fun postForm(
        @Valid @ModelAttribute messageForm: MessageForm,
        bindingResult: BindingResult,
        model: Model,
        pageable: Pageable,
    ): String {
        if (bindingResult.hasErrors()) {
            val message = messageService.findAll(pageable)
            model.addAttribute("message", message)
            return "top"
        }
        val user = userService.userCheck(messageForm.name)
        messageService.messageSave(user, messageForm)
        return "redirect:/top"
    }

    @GetMapping("reply/{messageId}")
    fun replyPage(
        @PathVariable messageId: Int,
        @ModelAttribute replyForm: ReplyForm,
        model: Model,
        pageable: Pageable,
    ): String {
        try {
            retryConfig.test2().execute {
                ThrowException().throwException()
            }
        } catch (e: ResponseStatusException) {
        }

        val replyMessage = replyService.findByReplyMessage(pageable, messageId)
        model.addAttribute("parentMessage", messageService.findByParentMessage(messageId))
        model.addAttribute("replyMessage", replyMessage)
        return "reply"
    }

    @PostMapping("reply/{messageId}")
    fun postReply(
        @PathVariable messageId: Int,
        @Valid @ModelAttribute replyForm: ReplyForm,
        bindingResult: BindingResult,
        model: Model,
        pageable: Pageable,
    ): String {
        if (bindingResult.hasErrors()) {
            val replyMessage = replyService.findByReplyMessage(pageable, messageId)
            model.addAttribute("parentMessage", messageService.findByParentMessage(messageId))
            model.addAttribute("replyMessage", replyMessage)
            return "reply"
        }
        val user = userService.userCheck(replyForm.name)
        replyService.replySave(user, replyForm, messageId)
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
