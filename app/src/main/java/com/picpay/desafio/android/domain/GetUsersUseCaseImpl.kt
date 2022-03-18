package com.picpay.desafio.android.domain

import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.data.repository.UserRepository
import com.picpay.desafio.android.service.ResponseAny

class GetUsersUseCaseImpl(
    private val userRepository: UserRepository
): GetUsersUseCase {
    override suspend fun invoke(): ResponseAny<List<User>> {
        return userRepository.getUsers()
    }
}