package com.bulletinboard.service

import com.bulletinboard.common.TestUtils.Companion.testUser
import com.bulletinboard.data.User
import com.bulletinboard.repository.UserRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class UserServiceTest {
    @Mock
    private lateinit var mockUserRepository: UserRepository

    @InjectMocks
    private lateinit var userService: UserService

    @Test
    fun `ユーザーが存在する場合、ユーザーIDが返却されること`() {
        whenever(mockUserRepository.findByName(testUser.name)).thenReturn(testUser)
        val a = userService.userCheck(testUser.name)

        verify(mockUserRepository).findByName(testUser.name)
        assertEquals(a.userId, testUser.userId)
    }

    @Test
    fun `ユーザーが存在しない場合、ユーザーが登録されること`() {
        val captor = argumentCaptor<User>()
        whenever(mockUserRepository.findByName(testUser.name)).thenReturn(null)
        whenever(mockUserRepository.save(any())).thenReturn(testUser)
        val a = userService.userCheck(testUser.name)

        verify(mockUserRepository).findByName(testUser.name)
        verify(mockUserRepository).save(captor.capture())
        assertEquals(a.userId, testUser.userId)
        assertEquals(testUser.name, captor.firstValue.name)
    }
}
