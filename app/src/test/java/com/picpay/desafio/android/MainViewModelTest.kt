package com.picpay.desafio.android

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.domain.GetUsersUseCase
import com.picpay.desafio.android.service.ResponseError
import com.picpay.desafio.android.service.ResponseSuccess
import com.picpay.desafio.android.ui.viewModel.MainViewModel
import com.picpay.desafio.android.utils.MainCoroutineRule
import com.picpay.desafio.android.utils.Status
import com.picpay.desafio.android.utils.shared_preferences.SharedPreferenceUtil
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.lang.Exception

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {
    private val  getUsersUseCase = mockk<GetUsersUseCase>(relaxed = true)
    private val  sharedPreferenceUtil = mockk<SharedPreferenceUtil>(relaxed = true)

    private val viewModel by lazy{
        MainViewModel(getUsersUseCase, sharedPreferenceUtil)
    }

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `when get user returns Success`() = runBlockingTest{
        val body =  listOf(User(
            "https://randomuser.me/api/portraits/men/9.jpg",
            "Eduardo Santos",
            1001,
            "@eduardo.santos"

        ))

        coEvery { getUsersUseCase.invoke() } returns ResponseSuccess(body)
        viewModel.loadUsersByApi()

        val statusPlacesStructureObserver = spyk<Observer<Status>>()
        viewModel.status.observeForever(statusPlacesStructureObserver)

       verify { statusPlacesStructureObserver.onChanged(Status.SUCCESS) }

    }

    @Test
    fun `when get user returns Error`() = runBlockingTest{

        coEvery { getUsersUseCase.invoke() } returns ResponseError(Exception())
        viewModel.loadUsersByApi()

        val statusPlacesStructureObserver = spyk<Observer<Status>>()
        viewModel.status.observeForever(statusPlacesStructureObserver)

        verify { statusPlacesStructureObserver.onChanged(Status.ERROR) }

    }

    @Test
    fun `when list not empty`() = runBlockingTest{
        val listUsers =  listOf(User(
            "https://randomuser.me/api/portraits/men/9.jpg",
            "Eduardo Santos",
            1001,
            "@eduardo.santos"

        ))

        coEvery { getUsersUseCase.invoke() } returns ResponseSuccess(listUsers)
        viewModel.loadUsersByApi()

        val listObserver = spyk<Observer<List<User>>>()
        viewModel.userList.observeForever(listObserver)

        verify { listObserver.onChanged(listUsers) }

    }

    @Test
    fun `when list empty`() = runBlockingTest{

        coEvery { getUsersUseCase.invoke() } returns ResponseError(Exception())
        viewModel.loadUsersByApi()

        val listObserver = spyk<Observer<List<User>>>()
        viewModel.userList.observeForever(listObserver)

        verify { listObserver.onChanged(emptyList()) }

    }
}