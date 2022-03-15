package com.picpay.desafio.android.domain

import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.service.ResponseAny

interface GetUsersUseCase {
    suspend operator fun invoke(): ResponseAny<List<User>>
}