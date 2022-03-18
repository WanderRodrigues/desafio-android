package com.picpay.desafio.android.data

import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.data.repository.UserRepository
import com.picpay.desafio.android.domain.GetUsersUseCaseImpl
import com.picpay.desafio.android.service.ResponseError
import com.picpay.desafio.android.service.ResponseSuccess
import com.picpay.desafio.android.utils.MainCoroutineRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.lang.Exception

@ExperimentalCoroutinesApi
class GetUsersUseCaseTest {

    private val userRepository = mockk<UserRepository>()
    private val useCase by lazy { GetUsersUseCaseImpl(userRepository) }

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun testInvokeSuccess() = mainCoroutineRule.runBlockingTest {
        val body =  listOf(
        User(
        "https://randomuser.me/api/portraits/men/9.jpg",
        "Eduardo Santos",
        1001,
        "@eduardo.santos")
        )
        coEvery { userRepository.getUsers() } returns ResponseSuccess(body)

        val response = useCase()

        assertEquals(body, (response as ResponseSuccess).body)
    }

    @Test
    fun testInvokeError() = mainCoroutineRule.runBlockingTest {

        coEvery { userRepository.getUsers() } returns ResponseError(Exception())

        val response = useCase()

        assertEquals(Exception().message, (response as ResponseError).errorBody)
    }


}