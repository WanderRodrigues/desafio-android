package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.service.ResponseAny

interface UserRepository {
    suspend fun getUsers(): ResponseAny<List<User>>
}