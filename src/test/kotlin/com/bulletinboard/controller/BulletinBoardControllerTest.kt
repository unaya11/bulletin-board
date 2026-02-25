package com.bulletinboard.controller

import com.bulletinboard.common.TestUtils.Companion.mockPage
import com.bulletinboard.common.TestUtils.Companion.testMessageDate
import com.bulletinboard.common.TestUtils.Companion.testMessageForm
import com.bulletinboard.common.TestUtils.Companion.testReplyForm
import com.bulletinboard.common.TestUtils.Companion.testUser
import com.bulletinboard.data.Message
import com.bulletinboard.data.Reply
import com.bulletinboard.service.MessageService
import com.bulletinboard.service.ReplyService
import com.bulletinboard.service.UserService
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.data.domain.Pageable
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.model
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.view

@WebMvcTest(BulletinBoardController::class)
class BulletinBoardControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockitoBean
    private lateinit var mockUserService: UserService

    @MockitoBean
    private lateinit var mockMessageService: MessageService

    @MockitoBean
    private lateinit var mockReplyService: ReplyService

    @Test
    fun `正常系_topページに遷移できること`() {
        whenever(mockMessageService.findAll(any<Pageable>())).thenReturn(mockPage())
        mockMvc
            .perform(get("/top"))
            .andExpect(status().isOk)
            .andExpect(view().name("top"))
            .andExpect(model().attribute("message", mockPage<Message>()))
    }

    @Test
    fun `正常系_topページにリダイレクトされること`() {
        whenever(mockUserService.userCheck(testUser.name)).thenReturn(testUser)
        mockMvc
            .perform(
                post(
                    "/top",
                ).param("name", testUser.name).param("title", testMessageDate.title)
                    .param("message", testMessageDate.message),
            ).andExpect(status().isFound)
            .andExpect(redirectedUrl("/top"))

        verify(mockMessageService).messageSave(testUser, testMessageForm)
    }

    @Test
    fun `返信ページに遷移できること`() {
        whenever(
            mockReplyService.findByReplyMessage(
                any<Pageable>(),
                eq(testMessageDate.messageId!!),
            ),
        ).thenReturn(mockPage())
        whenever(
            mockMessageService.findByParentMessage(testMessageDate.messageId!!),
        ).thenReturn(testMessageDate)

        mockMvc
            .perform(get("/reply/${testMessageDate.messageId}"))
            .andExpect(status().isOk)
            .andExpect(view().name("reply"))
            .andExpect(model().attribute("parentMessage", testMessageDate))
            .andExpect(model().attribute("replyMessage", mockPage<Reply>()))
    }

    @Test
    fun `返信ページにリダイレクトされること`() {
        whenever(mockUserService.userCheck(testUser.name)).thenReturn(testUser)
        mockMvc
            .perform(
                post("/reply/${testMessageDate.messageId}").param("name", testUser.name)
                    .param("reply", testMessageDate.message),
            ).andExpect(status().isFound)
            .andExpect(redirectedUrl("/reply/${testMessageDate.messageId}"))
        verify(mockReplyService).replySave(testUser, testReplyForm, testMessageDate.messageId!!)
    }

    @Test
    fun `検索ページに遷移できること`() {
        whenever(
            mockMessageService.messageSearch(any<Pageable>(), eq(testMessageDate.message)),
        ).thenReturn(mockPage())
        mockMvc
            .perform(
                get("/search").param("keyword", testMessageDate.message),
            ).andExpect(status().isOk)
            .andExpect(view().name("search"))
            .andExpect(model().attribute("search", mockPage<Message>()))
            .andExpect(model().attribute("keyword", testMessageDate.message))
    }
}
