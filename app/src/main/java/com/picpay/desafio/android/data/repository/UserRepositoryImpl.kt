package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.data.remote.PicPayService
import com.picpay.desafio.android.service.ResponseAny
import com.picpay.desafio.android.service.safeApiCall

class UserRepositoryImpl(
    private val picPayService: PicPayService
): UserRepository {
    override suspend fun getUsers(): ResponseAny<List<User>> {
        return safeApiCall{ picPayService.getUsers() }
    }

}