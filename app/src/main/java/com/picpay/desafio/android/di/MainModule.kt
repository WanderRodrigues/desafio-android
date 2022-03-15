package com.picpay.desafio.android.di

import com.picpay.desafio.android.data.remote.PicPayService
import com.picpay.desafio.android.data.repository.UserRepository
import com.picpay.desafio.android.data.repository.UserRepositoryImpl
import com.picpay.desafio.android.domain.GetUsersUseCase
import com.picpay.desafio.android.domain.GetUsersUseCaseImpl
import com.picpay.desafio.android.service.ServiceGenerator
import com.picpay.desafio.android.ui.viewModel.MainViewModel
import com.picpay.desafio.android.utils.shared_preferences.SharedPreferenceUtil
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object MainModule {

    fun allModules() = listOf(
        viewModels, useCases, repositories, services, utilsModule
    )

    private val viewModels = module {
        viewModel { MainViewModel(get(), get()) }
    }

    private val useCases = module {
        single<GetUsersUseCase> { GetUsersUseCaseImpl(get()) }
    }

    private val repositories = module{
        single<UserRepository>{UserRepositoryImpl(get())}
    }

    private val services = module {
        single { ServiceGenerator().generate() as PicPayService }
    }

    private val utilsModule = module {
        factory {
            SharedPreferenceUtil(androidContext())
        }
    }
}